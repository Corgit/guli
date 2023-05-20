package com.guli.seckill.feign;

import com.guli.common.utils.R;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/21 11:48
 */
@Slf4j
@Component
public class ProductFeignFallbaclFactory implements FallbackFactory<ProductFeign> {

    @Override
    public ProductFeign create(Throwable throwable) {
        log.info("调用商品服务失败！");
        return new ProductFeign() {
            @Override
            public R querySkuInfo(Long id) {
                return R.error("调用商品服务的查询接口失败！");
            }
        };
    }
}
