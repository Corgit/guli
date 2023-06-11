package com.guli.search.service;

import com.guli.search.ao.SuggesterAo;
import com.guli.search.vo.EsAddrVo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/5 16:57
 */
public interface SuggestService {

    void historyBatchUpdateFromEs() throws IOException;

    void batchToEs(List<EsAddrVo> vos);

    List<Map<String, String>> getSuggests(SuggesterAo ao);
}
