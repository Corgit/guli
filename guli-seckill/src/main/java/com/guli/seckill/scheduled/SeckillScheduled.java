package com.guli.seckill.scheduled;

import com.guli.seckill.service.SeckillService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/23 19:35
 */
@Slf4j
@Service // 需要加注解
public class SeckillScheduled {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedissonClient redissonClient;

    private static final String UPLOAD_LOCK = "upload:lock";

    @XxlJob("uploadHandler")
    public void upload3daysSessions() {

        // 因为秒杀服务可能会部署多个实例，使用redisson保证在分布式环境下不会重复上架
        RLock lock = redissonClient.getLock(UPLOAD_LOCK);
        lock.lock(3000, TimeUnit.MILLISECONDS);
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String start = formatter.format(LocalDate.now());
            String end = formatter.format(LocalDate.now().plusDays(2));
            log.info(">>>>>>>>>>> 上架" + start + "至" + end + "的秒杀活动");
            seckillService.upload3daySessions();
        } finally {
            lock.unlock();
        }
    }
}
