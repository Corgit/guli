package com.guli.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/24 16:32
 */
@EnableRedisHttpSession
@Configuration
public class SessionConfig {
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        // 设置域名，只有在请求这个域名及其子域名的时候才会设置cookie
//        cookieSerializer.setDomainName("guli.com");
        // 设置cookie名
        cookieSerializer.setCookieName("GULI");
        return cookieSerializer;
    }

    @Bean
    public RedisSerializer<Object> springSessionDefault() {
        return new GenericJackson2JsonRedisSerializer();
    }

}
