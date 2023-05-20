package com.guli.orders.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @describe：RabbitMQ的配置
 * @author: Ryan_Wu
 * @Date: 2022/4/20 15:35
 */
@Configuration
public class RabbitMqConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 设置确认回调
     */
    @PostConstruct // 在RabbitMqConfig配置类对象创建完成之后执行此方法
    public void setConfirm() {
        // 设置exchange到queue的确认回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                System.out.println("this is return callback ===>" + message);
            }
        });
        // 设置publisher到broker的确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                System.out.println("this is confirm callback ===>" + correlationData);
            }
        });
    }

    /**
     * 设置序列化为json
     */
    @Bean
    public MessageConverter toJson() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 设置RabbitMQ的连接
     */
//    @Bean
//    public void connect() throws IOException, TimeoutException {
//        ConnectionFactory connection = new ConnectionFactory();
//        connection.setHost("192.168.56.10");
//        connection.setPort(5672);
//        connection.setVirtualHost("/");
//        connection.newConnection();
//    }
}
