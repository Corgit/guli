package com.guli.product.feign;

import com.guli.common.utils.R;
import com.guli.product.vo.EsAddrVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/7 15:14
 */
@FeignClient(name = "guli-search")
public interface SearchFeign {

    @PostMapping("/search/suggest/es/to/batch")
    R toEsBatch(@RequestBody List<EsAddrVo> vos);

}
