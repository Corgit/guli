package com.guli.search.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.guli.search.config.GuliEsConfig;
import com.guli.search.entity.EsResponseEntity;
import com.guli.search.entity.SubHits;
import com.guli.search.entity.Tokens;
import com.guli.search.feign.ProductFeign;
import com.guli.search.service.SuggestService;
import com.guli.search.vo.AddressVO;
import com.guli.search.vo.EsAddrVo;
import com.guli.search.vo.EsAddressVo;
import com.guli.search.vo.TokensVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/5 16:59
 */
@Slf4j
@Service
public class SuggestServiceImpl implements SuggestService {

    static final String IK_ANALYZER = "ik_max_word";

    private Integer street_weight = 10;
    private Integer district_weight = 8;
    private Integer city_weight = 4;

    @Autowired
    private RestClient restClient;

    @Autowired
    private RestHighLevelClient highLevelClient;

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

    @Override
    public void batchToEs(List<EsAddrVo> vos) {
        if(vos != null && vos.size() > 0) {
            // 构造index请求，使用bulk批量插入
            BulkRequest bulkRequest = new BulkRequest();
            for(EsAddrVo v : vos) {
                IndexRequest indexRequest = new IndexRequest("address");
                EsAddressVo addr = BeanUtil.copyProperties(v, EsAddressVo.class);
                indexRequest.id(v.getId().toString());

                // 生成suggest字段的值，先分词
                List<Map<String, Object>> tokensList = new ArrayList<>();
                // 街道、区域、全地址字段分词，注意空字段可以导致NPE
                TokensVo streetVo = analyzeWords(addr.getStreet(), street_weight);
                if (streetVo != null) {
                    tokensList.add(streetVo.getAnalysis());
                }
                TokensVo districtVo = analyzeWords(addr.getDistrict(), district_weight);
                if (districtVo != null) {
                    tokensList.add(districtVo.getAnalysis());
                }
                TokensVo addrVo = analyzeWords(addr.getFulladdr(), city_weight);
                if(addrVo != null) {
                    // 去重，注意removeAll方法是原地修改，会直接改变原来addrVo变量里tokens数组、input数组的值
                    List<String> addrTokens = addrVo.getTokens();
                    if(streetVo != null){
                        addrTokens.removeAll(streetVo.getTokens());
                    }
                    if(districtVo != null){
                        addrTokens.removeAll(districtVo.getTokens());
                    }
                    addrVo.getAnalysis().put("input", addrTokens);
                    tokensList.add(addrVo.getAnalysis());
                }
                addr.setSuggest(tokensList);

                // 添加bulk请求
                indexRequest.source(JSON.toJSONString(addr), XContentType.JSON);
                bulkRequest.add(indexRequest);
            }

            // 保存到ES
            if (bulkRequest.numberOfActions() > 0) {
                try {
                    BulkResponse bulkResponse = highLevelClient.bulk(bulkRequest, GuliEsConfig.COMMON_OPTIONS);
                    if(bulkResponse.hasFailures()) {
                        log.info("bulk批量操作异常：" + bulkResponse.buildFailureMessage());
                    }
                }catch(IOException e) {
                    log.info(e.toString());
                }
            }
        }
    }

    /**
     * @Description: 分词
     * @Params: 输入需要分词的字符串，默认分词器为ik_max_word
     * @Return: 分词返回tokens数组，组装成TokensVo
     * @Author: Ryan_Wuyx
     * @Date: 2023/6/8 16:03
     */
    public TokensVo analyzeWords(String text, Integer weight) {
        // 空串判别，否则会返回null造成下一步出现NPE
        if (text != null && text.length() > 0) {
            // 使用analyze API
            Request request = new Request("POST", "_analyze");
            Map<String, String> map = new HashMap<>();
            map.put("analyzer", IK_ANALYZER);
            map.put("text", text);
            request.setJsonEntity(JSON.toJSONString(map));
            try {
                // 分词，将JSON字符串转换为list
                Response response = restClient.performRequest(request);
                String s = EntityUtils.toString(response.getEntity());
                // 如果text不是空串，那么分词后tokens一定不为空
                JSONArray tokens = JSONObject.parseObject(s).getJSONArray("tokens");
                List<Tokens> list = tokens.toJavaList(Tokens.class);
                List<String> wordList = new ArrayList<>();
                for (Tokens t : list) {
                    wordList.add(t.getToken());
                }
                //组装成vo
                TokensVo vo = new TokensVo();
                vo.setTokens(wordList);
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("input", wordList);
                if (weight != null) {
                    dataMap.put("weight", weight);
                } else {
                    dataMap.put("weight", 1);
                }
                vo.setAnalysis(dataMap);
                return vo;
            } catch (Exception e) {
                log.info("分词出现异常 >>>>> 文本：【" + text + "】，异常信息：" + e);
                return null;
            }
        } else {
            return  null;
        }
    }
}