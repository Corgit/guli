package com.guli.product.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/21 10:25
 */
@EnableFeignClients(basePackages = "com.guli.product.**")
@SpringBootApplication
@ComponentScan(basePackages = "com.guli.**")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.guli.product.dao.**")
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
