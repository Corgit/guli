package com.guli.search.controller;

import com.guli.common.utils.R;
import com.guli.search.config.GuliEsConfig;
import org.apache.http.HttpEntity;
import org.apache.http.RequestLine;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/2 10:27
 */
@RequestMapping("/search/index")
@RestController
public class IndexController {

    @Autowired
    private RestClient client;

    @GetMapping("/test")
    public void xtest() {
        // 可打印出client对象说明注入成功
        // client ==org.elasticsearch.client.RestClient@2bfc2f8b
        System.out.println("client ==" + client);
    }

    @GetMapping("/mget")
    public R multiGet() throws IOException {
        // 构造mget方法
        Request request = new Request("get", "/rainhall/_mget");
        request.setEntity(new NStringEntity("{\"ids\":[7]}", ContentType.APPLICATION_JSON));

        // 执行查询
        Response response = client.performRequest(request);

        // 打印请求信息
        String line = response.getRequestLine().toString();
        System.out.println(line);
        // 获取命中信息
        String body = EntityUtils.toString(response.getEntity());
        System.out.println(body);

        return R.ok("requestLine == "+ line + "entity == "+ body);
    }

    @GetMapping("/match")
    public R match() {
        return R.ok();
    }

}
