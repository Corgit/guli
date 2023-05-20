package com.guli.seckill.ao;

import lombok.Data;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/24 15:43
 */
@Data
public class SeckillAO {

    private Long sessionId;
    private Long skuId;
    private Integer num;
    private String code;

}
