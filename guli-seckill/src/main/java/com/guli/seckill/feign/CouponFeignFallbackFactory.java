package com.guli.seckill.feign;

import com.guli.common.utils.R;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/20 15:56
 */
@Slf4j
@Component
public class CouponFeignFallbackFactory implements FallbackFactory<CouponFeign> {
    @Override
    public CouponFeign create(Throwable throwable) {
        log.error("远程调用coupon服务失败！");
        return new CouponFeign() {
            @Override
            public R getSessionDetailB3day(String startTime, String endTime) {
                return R.error("远程调用秒杀上架接口失败！");
            }
        };
    }
}
