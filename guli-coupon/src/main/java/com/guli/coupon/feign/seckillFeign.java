package com.guli.coupon.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/4 9:47
 */
@FeignClient(name = "guli-seckill")
public interface seckillFeign {
}
