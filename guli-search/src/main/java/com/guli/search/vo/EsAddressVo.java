package com.guli.search.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/7 16:45
 */
@Data
public class EsAddressVo {

    private String fulladdr;
    private String district;
    private String street;
    private List<Map<String, Object>> suggest;

}
