package com.guli.product.controller;

import cn.hutool.core.io.IoUtil;
import com.guli.common.utils.QRCodeUtil;
import com.guli.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: 二维码生成与识别接口
 * @Author: Ryan_Wuyx
 * @Date: 2023/10/23 17:29
 */
@Api(tags = "二维码接口")
@Slf4j
@RestController
@RequestMapping("product/qrcode")
public class QRCodeController {

    // 注入问题：@Resoure、@Bean、@Configuration、@Autowired的区别
    // converter以及sendError报错问题

    // Multipartfile[] file多文件上传, POST, 大小限制（默认100M上限）
//    @PostMapping("/test/multi")
//    public void test(MultipartFile[] files) {
//        // 必须使用post和body
//        System.out.println(files.length);
//        for(int i = 0; i < files.length; i++) {
//            System.out.println(files[i].getOriginalFilename());
//            System.out.println(files[i].getContentType());
//            System.out.println(files[i].getSize()); // 以字节为单位
//        }
//    }

//    @Resource
    @Autowired
    QRCodeUtil qrCodeUtil;

    @ApiOperation("生成二维码")
    @PostMapping("/get/stream")
    public R createQRCodeStream(String content, MultipartFile file, HttpServletResponse response) {
        if(content != null && content.length() > 0) {
            if(file != null && !file.isEmpty()) {
                // 图片判断：传递的文件类型只能是图片
                log.info("上传的文件类型为 >>> " + file.getContentType().split("/")[0]);
                if(!file.getContentType().split("/")[0].equals("image")) {
                    return R.error("请上传图片文件！");
                }
                String path = "E:\\QRCode\\";
                File logo = new File(path + file.getOriginalFilename());
                if(logo.getParentFile() != null && !logo.getParentFile().exists()) {
                    logo.getParentFile().mkdirs();
                }
                FileOutputStream outputStream = null;
                try {
                    InputStream inputStream = file.getInputStream();
                    outputStream = new FileOutputStream(logo);
                    // 当没有工具类时，使用getBytes()方法，然后在循环中将每个byte写入stream
                    IoUtil.copy(inputStream, outputStream);
                } catch (IOException e){
                    e.printStackTrace();
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.flush();
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                qrCodeUtil.generateQRCodeToStream(content, logo, response);
            } else {
                qrCodeUtil.generateQRCodeToStream(content, null, response);
            }
        } else {
            return R.error("信息量为空！");
        }
        return R.ok();
    }

    @ApiOperation("识别二维码")
    @PostMapping("/get/decode")
    public R decodeQrCode(MultipartFile file) {
        if(file != null && !file.isEmpty()) {
            try {
                return R.ok(qrCodeUtil.decode(file.getInputStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return R.ok();
    }
}
