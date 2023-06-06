package com.guli.coupon.controller;

import java.io.*;
import java.util.Date;
import java.util.List;

import com.guli.coupon.ao.SeckillQueryAO;
import com.guli.coupon.entity.SeckillSessionEntity;
import com.guli.coupon.entity.SeckillSkuRelationEntity;
import com.guli.coupon.service.SeckillSessionService;
import com.guli.coupon.service.SeckillSkuRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import com.guli.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 优惠服务接口
 *
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:06:37
 */

@Api(tags = "优惠活动API")
@RefreshScope
@RestController
@RequestMapping("coupon/coupon")
public class CouponController {

    @Autowired
    private SeckillSessionService seckillSessionService;

    @Autowired
    private SeckillSkuRelationService relationService;

    /**
     * 测试获取配置项的值
     */
    @Value("${coupon.user.size}")
    private String size;

    @ApiOperation("获取配置信息")
    @GetMapping("/getInfo")
    public R getInfo() {
        return R.ok().put("size", size);
    }

    /**
     * 测试数据库连接
     * @return
     */
    @ApiOperation("获取一条记录")
    @GetMapping("/getone")
    public R getOne() {
        return R.ok().put("result", seckillSessionService.getone());
    }

    /**
     * 新增秒杀场次信息
     * @return
     */
    @ApiOperation("新增秒杀场次信息")
    @PostMapping("/seckill/add/session")
    public R insertSeckillSession(@RequestBody List<SeckillSessionEntity> entityList) {
        if(seckillSessionService.insertSeckillSession(entityList)) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 删除秒杀场次
     * @return
     */
    @ApiOperation("删除秒杀场次")
    @PostMapping("/seckill/del/session")
    public R deleteSeckillSession(@RequestBody List<SeckillSessionEntity> list) {
        if(seckillSessionService.deleteSeckillSession(list)) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("新增秒杀场次关联商品信息")
    @PostMapping("/seckill/relate")
    public R skuRelate(@RequestBody List<SeckillSkuRelationEntity> list) {
        if(relationService.insertRelation(list)) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("分页查询秒杀场次信息")
    @PostMapping("/seckill/session/query")
    public R querySeckillSession(@RequestBody SeckillQueryAO ao) {
        return R.ok().put("result",seckillSessionService.querySeckillSessionInfo(ao));
    }

    @ApiOperation("分页查询秒杀场次信息-注解")
    @PostMapping("/seckill/session/anno")
    public R querySeckillSessionWithAnno(@RequestBody SeckillQueryAO ao) {
        return R.ok().put("result",seckillSessionService.querySeckillSessionInfoWithAnnotation(ao));
    }

    @ApiOperation("关联查询秒杀活动详情")
    @PostMapping("/seckill/detail/query")
    public R querySeckillDetail(@RequestParam("startTime")String startTime, @RequestParam("endTime")String endTime) {
        if(startTime != null && endTime != null) {
            return R.ok().put("result", seckillSessionService.querySeckillDetail(startTime, endTime));
        } else {
            return R.error();
        }
    }

    @ApiOperation("查询指定时间区间秒杀活动")
    @PostMapping("/seckill/detail/query/to")
    public R querySeckillDetailTo(@RequestParam("startTime")String startTime, @RequestParam("endTime")String endTime) {
        if(startTime != null && endTime != null) {
            return R.ok().put("result", seckillSessionService.querySeckillDetailTo(startTime, endTime));
        } else {
            return R.error();
        }
    }

    @ApiOperation("图片、视频上传接口")
    @PostMapping("/file/upload")
    public R saveImage(@RequestParam("image") MultipartFile image, @RequestParam("video") MultipartFile video) throws IOException {
        if(image != null && image.getSize() > 0) {
            System.out.println("name: " + image.getName());
            System.out.println("original: " + image.getOriginalFilename());
            // 创建输入流
            InputStream input = image.getInputStream();
            // 文件输出路径，必须是文件的全路径名
            String path = "E:\\data\\test\\" + image.getOriginalFilename();
            // 创建输出流
            OutputStream out = new FileOutputStream(path);
            try {
                byte[] buffer = new byte[64];
                // 写出到本地
                while(input.read(buffer) != -1) {
                    out.write(buffer, 0, 64);
                }
            } finally {
                // IO流必须要关闭，否则本地文件会裂开并且提示JVM占用中
                input.close();
                out.close();
            }

        }

        if(video != null && video.getSize() > 0) {
            System.out.println("VideoName: " + video.getName());
            System.out.println("VideoOriginalName: " + video.getOriginalFilename());
            // 创建输入流
            InputStream input = video.getInputStream();
            // 文件输出路径，必须是文件的全路径名
            String path = "E:\\data\\test\\" + video.getOriginalFilename();
            // 创建输出流
            OutputStream out = new FileOutputStream(path);
            try {
                byte[] buffer = new byte[512];
                // 写出到本地
                while(input.read(buffer) != -1) {
                    out.write(buffer, 0, 512);
                }
            } finally {
                // IO流必须要关闭，否则本地文件会裂开并且提示JVM占用中
                input.close();
                out.close();
            }
        }

        return R.ok();
    }

}
