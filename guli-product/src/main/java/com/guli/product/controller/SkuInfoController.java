package com.guli.product.controller;

import com.guli.product.service.CategoryService;
import com.guli.product.vo.AddressVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.guli.product.entity.SkuInfoEntity;
import com.guli.product.service.SkuInfoService;
import com.guli.common.utils.PageUtils;
import com.guli.common.utils.R;

import java.util.List;

/**
 * sku信息
 *
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 11:56:52
 */
@Api(tags = "商品服务API")
@RestController
@RequestMapping("product/skuinfo")
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查询sku的详细信息")
    @PostMapping("/query")
    public R querySkuInfo(@RequestParam("id") Long id) {
        if(id != null) {
            return R.ok().put("result", skuInfoService.querySkuInfoById(id));
        }
        return R.error();
    }

    @ApiOperation("查询分类目录树图")
    @GetMapping("/cat/tree")
    public R queryCategoryTree() {
        return R.ok().put("data", categoryService.queryCategoryTree());
    }

    @ApiOperation("一次性将ES数据存入MySQL")
    @PostMapping("/batch/es")
    public R batchUpdateEs(@RequestBody List<AddressVo> vos) {
        skuInfoService.esHistoryUpdate(vos);
        return null;
    }


}
