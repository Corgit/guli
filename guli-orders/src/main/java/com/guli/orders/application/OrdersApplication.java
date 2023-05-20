package com.guli.orders.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/4/17 16:51
 */
@EnableRabbit
@EnableKafka
@ComponentScan(basePackages = "com.guli.**")
@MapperScan(basePackages = "com.guli.orders.dao.**")
@SpringBootApplication
public class OrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }
}
