package com.guli.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.guli.search.entity.EsResponseEntity;
import com.guli.search.entity.SubHits;
import com.guli.search.feign.ProductFeign;
import com.guli.search.service.SuggestService;
import com.guli.search.vo.AddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/5 16:59
 */
@Slf4j
@Service
public class SuggestServiceImpl implements SuggestService {

    @Autowired
    private RestClient restClient;

    @Autowired
    private ProductFeign productFeign;

    @Override
    public void historyBatchUpdate() throws IOException {

        // 使用_search endpoint查出index rainhall下所有数据
        // GET /rainhall/_search
        //{
        //  "_source":{
        //    "excludes": ["age", "flip"]
        //  },
        //  "from":0,
        //  "size":126
        //}
        Request request = new Request("get", "/rainhall/_search");
        request.setEntity(new NStringEntity("{\"_source\":{\"excludes\":[\"age\", \"flip\"]},\"from\":0,\"size\":126}",
                ContentType.APPLICATION_JSON));
        Response response = restClient.performRequest(request);

        // 类型转换
        String body = EntityUtils.toString(response.getEntity());
        EsResponseEntity e = JSONObject.parseObject(body, new TypeReference<EsResponseEntity>(){});
        List<SubHits> hits = e.getHits().getHits();
        List<AddressVO> vos = hits.stream().map( h -> {
                    AddressVO vo = new AddressVO();
                    vo.setId(h.get_id());
                    vo.setAddress(h.get_source().getAddress());
                    vo.setName(h.get_source().getName());
                    vo.setRole(h.get_source().getRole());
                    vo.setSize(h.get_source().getSize());
                    return vo;
                }).collect(Collectors.toList());

        // 发送到product服务
        productFeign.batchUpdateEs(vos);
    }
}
