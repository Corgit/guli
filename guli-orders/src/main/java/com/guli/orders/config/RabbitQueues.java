package com.guli.orders.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/4/21 14:27
 */
@Configuration
public class RabbitQueues {

    @Value("${rabbitmq.x-message-ttl}")
    private long ttl;

    @Value("${rabbitmq.x-dead-letter-exchange}")
    private String deadLetterExchange;

    @Value("${rabbitmq.x-dead-letter-routing-key}")
    private String deadLetterRkey;

    @Value("${rabbitmq.x-queue-mode}")
    private String isLazyQueue;

    // 延时、惰性队列
    @Bean
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", ttl);
        args.put("x-dead-letter-exchange", deadLetterExchange);
        args.put("x-dead-letter-routing-key", deadLetterRkey);
        args.put("x-queue-mode", isLazyQueue);
        return new Queue("delay.queue", true,
                false, false, args);
    }

    // 死信路由，通常死信路由也是普通路由，当延时队列的消息过期后，就返回原路由，原路由通过死信的routing key路由到死信队列
    @Bean
    public Exchange orderExchange() {
        return new TopicExchange("order-service-exchange");
    }

    // 死信队列
    @Bean
    public Queue deadLetterQueue() {
        return new Queue("order.release.queue");
    }

    // 死信路由与延时队列的绑定
    @Bean
    public Binding orderCreateBinding() {
        return new Binding("delay.queue",
                Binding.DestinationType.QUEUE,
                "order.service.exchange",
                "order.create",
                null);
    }

    // 死信路由与死信队列的绑定
    @Bean
    public Binding orderReleaseOrderBinding() {
        return new Binding("order.release.queue",
                Binding.DestinationType.QUEUE,
                "order.service.exchange",
                deadLetterRkey,
                null);
    }
}
