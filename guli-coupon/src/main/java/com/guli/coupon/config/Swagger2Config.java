package com.guli.coupon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 配置basePackage，即swagger扫描的controller接口位置
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.guli.coupon.controller"))
                .paths(PathSelectors.any()).build();
    }

    /**
     * 配置swagger页面展示文字
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置页面标题
                .title("使用swagger2构建后端api接口文档")
                // 设置联系人
                .contact(new Contact("Ryan", "", ""))
                // 描述
                .description("商城-优惠服务文档")
                // 定义版本号
                .version("1.0").build();
    }

}