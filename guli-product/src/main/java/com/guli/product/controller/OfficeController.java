package com.guli.product.controller;

import com.guli.common.utils.R;
import com.guli.product.service.OfficeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description:
 * @Author: Ryan_Wuyx
 * @Date: 2023/9/27 16:01
 */
@Api(tags = "商品服务office接口")
@RestController
@RequestMapping("product/office")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @ApiOperation("使用Excel文件批量上传商品信息")
    @PostMapping("/upload/excel")
    public R batchUploadSpuInfoByExcel() {
        officeService.uploadSpuInfoByExcel();
        return R.ok();
    }

    @ApiOperation("下载商品信息表格")
    @GetMapping("/download/excel")
    public R downloadSpuExcel(HttpServletResponse response) {
        System.out.println("response header >>>>>>>> " + response.getHeaderNames());
        officeService.downloadSpu(response);
        return R.ok();
    }

}
