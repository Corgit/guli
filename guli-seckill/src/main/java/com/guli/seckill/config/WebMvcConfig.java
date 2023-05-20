package com.guli.seckill.config;

import com.guli.seckill.Interceptor.SeckillInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/24 17:25
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    SeckillInterceptor seckillInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns后配置拦截路径
        registry.addInterceptor(seckillInterceptor)
//                .addPathPatterns("/**") // 表示拦截所有路径
            .addPathPatterns("/seckill/**") // 表示拦截/seckill目录下的所有路径
            .excludePathPatterns("/seckill/user/**"); // 表示排除/user目录下的请求，这些请求不拦截
    }
}
