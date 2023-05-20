package com.guli.orders.config;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/5/6 15:52
 */
@Configuration
public class KafkaConsumers {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "thisTp")
    public void consume(String msg) {
        if(msg != null) {
            System.out.println(msg);
        }
    }

    @KafkaListener(topics = "SeckillOrders")
    public void seckill(String orderSn) {
        System.out.println("订单号为：" + orderSn);
    }
}
