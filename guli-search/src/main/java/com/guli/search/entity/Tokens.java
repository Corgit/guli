package com.guli.search.entity;

import lombok.Data;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/8 15:19
 */
@Data
public class Tokens {
    private String token;
    private String type;
    private Integer start_offset;
    private Integer end_offset;
    private Integer position;
}
