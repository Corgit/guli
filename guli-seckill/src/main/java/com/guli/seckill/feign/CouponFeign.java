package com.guli.seckill.feign;

import com.guli.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/20 15:42
 */
@FeignClient(
        name = "guli-coupon",
        fallbackFactory = CouponFeignFallbackFactory.class
)
public interface CouponFeign {

    @PostMapping("/coupon/coupon/seckill/detail/query/to")
    R getSessionDetailB3day(@RequestParam("startTime") String startTime,
                            @RequestParam("endTime") String endTime);
}
