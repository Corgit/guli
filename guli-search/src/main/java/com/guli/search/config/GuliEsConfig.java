package com.guli.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/2 9:36
 */
@Configuration
public class GuliEsConfig {

    @Value("${es.server.host}")
    private String esHost;
    @Value("${es.server.port}")
    private int esPort;
    @Value("${es.server.scheme}")
    private String esScheme;

    // 在容器中后放入一个client，集群时放入多个HttpHost
    @Bean
    public RestClient generateEsClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost(esHost, esPort, esScheme)).build();
        return restClient;
    }
}
