package com.guli.search.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/2 9:42
 */
@EnableFeignClients(basePackages = "com.guli.search.**")
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.guli.**"})
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
