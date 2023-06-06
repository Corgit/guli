package com.guli.search.feign;

import com.guli.common.utils.R;
import com.guli.search.vo.AddressVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/6 16:56
 */
@FeignClient(name = "guli-product")
public interface ProductFeign {

    @PostMapping("/product/skuinfo/batch/es")
    R batchUpdateEs(@RequestBody List<AddressVO> vos);

}
