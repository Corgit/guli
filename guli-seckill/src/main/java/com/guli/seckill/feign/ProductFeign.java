package com.guli.seckill.feign;

import com.guli.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/21 11:44
 */
@FeignClient(name = "guli-product", fallbackFactory = ProductFeignFallbaclFactory.class)
public interface ProductFeign {

    @PostMapping("/product/skuinfo/query")
    R querySkuInfo(@RequestParam("id") Long id);
}
