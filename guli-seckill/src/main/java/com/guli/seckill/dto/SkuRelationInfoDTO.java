package com.guli.seckill.dto;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/21 10:13
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SkuRelationInfoDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 活动id
     */
    @ApiModelProperty("活动id")
    private Long promotionId;

    /**
     * 活动场次id
     */
    @ApiModelProperty("场次id")
    private Long promotionSessionId;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long skuId;

    /**
     * 秒杀价格
     */
    @ApiModelProperty("秒杀价格")
    private BigDecimal seckillPrice;

    /**
     * 秒杀总量
     */
    @ApiModelProperty("秒杀总量")
    private BigDecimal seckillCount;

    /**
     * 每人限购数量
     */
    @ApiModelProperty("每人限购数量")
    private BigDecimal seckillLimit;

    /**
     * 排序
     */
    @ApiModelProperty("排序展示")
    private Integer seckillSort;

    /**
     * 是否有效
     */
    @ApiModelProperty(hidden = true)
    private Integer valid;

    /**
     * 随机码
     */
    private String code;

    @ApiModelProperty("秒杀开始时间")
    private Long startTime;

    @ApiModelProperty("秒杀结束时间")
    private Long endTime;

    /**
     * 商品详细信息
     */
    private SkuInfoDTO skuInfo;
}
