package com.guli.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 秒杀活动商品关联
 * 
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:06:36
 */
@Data
@TableName("sms_seckill_sku_relation")
public class SeckillSkuRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
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

}
