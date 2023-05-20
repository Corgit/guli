package com.guli.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.guli.common.utils.R;
import com.guli.seckill.Interceptor.SeckillInterceptor;
import com.guli.seckill.ao.SeckillAO;
import com.guli.seckill.dto.SkuInfoDTO;
import com.guli.seckill.dto.SkuRelationInfoDTO;
import com.guli.seckill.feign.CouponFeign;
import com.guli.seckill.feign.ProductFeign;
import com.guli.seckill.service.SeckillService;
import com.guli.seckill.vo.SeckillInfoRedisVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/20 14:35
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private CouponFeign couponFeign;

    @Autowired
    private ProductFeign productFeign;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SeckillInterceptor interceptor;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // 线程池最好写在方法外以公用，而不是每次需要线程的时候都创建一个新的线程池
    private final ExecutorService poolService = Executors.newCachedThreadPool();

    private static final String SESSION_PREFIX = "seckill:sessions:";
    private static final String SKU_PREFIX = "seckill:skus";
    private static final String STOCK_PREFIX = "seckill:stock:";
    private static final String SECKILL_USER_PREFIX = "seckill:user:";

    /**
     * 幂等性处理：
     *    防止重复抢购，且注意单次抢购件数符合规定但抢购多次的情况，因此一个用户id只能下单一次，以最后一次为准
     *    每个用户在抢购前需要现在redis登记信息，key为 SECKILL_USER_PREFIX + 登录session，值为下单件数，过期时间为秒杀结束时间减去当前时间
     */
    @Override
    public HashMap<String, String> kill(SeckillAO ao) {

        String prefix = ao.getSessionId() + "_" + ao.getSkuId();
        Object seckillInfo = redisTemplate.opsForHash().get(SKU_PREFIX, prefix);
        if(seckillInfo != null) {
            SkuRelationInfoDTO dto = JSON.parseObject(seckillInfo.toString(), new TypeReference<SkuRelationInfoDTO>(){});
            // 1、时间、件数判断
            long current = System.currentTimeMillis();
            if(current >= dto.getStartTime() && current <= dto.getEndTime()
                    && ao.getNum() > 0 && ao.getNum() <= dto.getSeckillLimit().intValue()) {
                // 2、随机码、商品id判断
                if(ao.getCode().equals(dto.getCode()) && ao.getSkuId().equals(dto.getSkuId()) && ao.getSessionId().equals(dto.getPromotionSessionId())) {
                    // 3、幂等性处理，判断是否重复抢购
                    Boolean res = redisTemplate.opsForValue().setIfAbsent(SECKILL_USER_PREFIX + prefix + ":" + interceptor.getSession(),
                            ao.getNum().toString(), dto.getEndTime() - current, TimeUnit.MILLISECONDS);
                    if(res != null && res) {
                        // 4、锁定库存
                        RSemaphore semaphore = redissonClient.getSemaphore(STOCK_PREFIX + ao.getCode());
                        boolean seckillResult = semaphore.tryAcquire(ao.getNum());
                        if(seckillResult) {
                            // 5、生成订单号，发送MQ，返回抢购结果
                            String orderSn = UUID.randomUUID().toString();
                            HashMap<String, String> map = new HashMap<>();
                            map.put("result", "0");
                            map.put("msg", orderSn);
                            kafkaTemplate.send("SeckillOrders", orderSn);
                            return map;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void upload3daySessions() {
        // 一、请求优惠服务，查询需要上传的数据
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String start = formatter.format(LocalDate.now()) + " " + LocalTime.MIN;
        String end = formatter.format(LocalDate.now().plusDays(2)) + " " + LocalTime.MAX;
        R res = couponFeign.getSessionDetailB3day(start, end);

        // 二、结果提取，使用fastjson将object反序列化为需要的类型
        Object data = res.get("result");
        if(data != null) {
            // 反序列化:
            // 1、第一步将其转换为json字符串
            String str = JSON.toJSONString(data);
            // 2、第二步使用parseObject方法将json字符串转换为指定的类型。TypeReference必须带小括号和大括号
            List<SeckillInfoRedisVO> list = JSON.parseObject(str, new TypeReference<List<SeckillInfoRedisVO>>(){});

            // 三、异步查询商品信息，上传至redis
            for(SeckillInfoRedisVO vo : list) {
                long startTime = vo.getStartTime().getTime();
                long endTime = vo.getEndTime().getTime();
                String time = startTime + "_" + endTime;
                String sessionKey = SESSION_PREFIX + time;
                // TODO: 设置过期时间
                // 1、上传session信息
                redisTemplate.opsForHash().put(sessionKey, vo.getId().toString(), JSON.toJSONString(vo));
                // 2、上传sku信息和库存信息
                if(vo.getRelatedSkus() != null && vo.getRelatedSkus().size() > 0) {
                    poolService.execute(new Thread(()->{
                        this.upload3daysSkus(vo.getRelatedSkus(), startTime, endTime);
                    }));
                }
            }
        }
    }

    // 异步查询商品信息，上传至redis，打印线程号
    private void upload3daysSkus(List<SkuRelationInfoDTO> list, long start, long end) {

        log.info("当前线程id: " + Thread.currentThread().getId());

        for(SkuRelationInfoDTO dto : list) {
            String prefix = dto.getPromotionSessionId() + "_" +dto.getSkuId();
            if(!redisTemplate.opsForHash().hasKey(SKU_PREFIX, prefix)) {
                // 查询商品属性信息
                R res = productFeign.querySkuInfo(dto.getSkuId());
                Object data = res.get("result");

                if(data != null) {
                    // 上传商品信息
                    String skuDetail = JSON.toJSONString(data);
                    SkuInfoDTO skuInfo = JSON.parseObject(skuDetail, new TypeReference<SkuInfoDTO>(){});
                    dto.setSkuInfo(skuInfo);
                    String code = UUID.randomUUID().toString().replace("-", "");
                    dto.setCode(code);
                    dto.setStartTime(start);
                    dto.setEndTime(end);
                    redisTemplate.opsForHash().put(SKU_PREFIX, prefix, JSON.toJSONString(dto));

                    // 上传库存信息，redisson应该拿随机码作为信号量
                    String randKey = STOCK_PREFIX + code;
                    RSemaphore sema = redissonClient.getSemaphore(randKey);
                    sema.trySetPermits(dto.getSeckillLimit().intValue());
                }
            }
        }
    }

}
