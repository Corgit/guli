package com.guli.coupon.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2021/9/8 12:14
 */
@EnableFeignClients(basePackages = "com.guli.coupon.**")
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = {"com.guli.coupon.dao.**"}) // 需要操作数据库时扫描到mapper
@ComponentScan(basePackages = {"com.guli.**"}) // 扫描到所有组件
public class CouponApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class, args);
    }
}
