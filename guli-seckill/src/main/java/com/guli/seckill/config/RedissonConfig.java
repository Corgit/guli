package com.guli.seckill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/22 17:21
 */
@Configuration
public class RedissonConfig {

    @Bean
    RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.56.10:6379").setDatabase(0);
        return Redisson.create(config);
    }

}
