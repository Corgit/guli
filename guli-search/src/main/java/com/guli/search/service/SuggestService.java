package com.guli.search.service;

import com.guli.search.vo.EsAddrVo;

import java.io.IOException;
import java.util.List;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/5 16:57
 */
public interface SuggestService {

    void historyBatchUpdate() throws IOException;

    void batchToEs(List<EsAddrVo> vos);
}
