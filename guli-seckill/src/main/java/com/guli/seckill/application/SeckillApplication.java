package com.guli.seckill.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/16 16:46
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.guli.seckill.**")
@SpringBootApplication
@ComponentScan(basePackages = {"com.guli.**"}) // 开启ComponentScan，否则扫描不到controller、service等
public class SeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }
}
