package com.guli.search.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/8 11:37
 */
@Data
public class TokensVo {

    private List<String> tokens;
    private Map<String, Object> analysis;

}
