package com.guli.search.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/6 15:54
 */
@Data
public class SubHits {

    private BigDecimal _score;
    private String _index;
    private String _type;
    private String _id;
    private AddressSource _source;
}
