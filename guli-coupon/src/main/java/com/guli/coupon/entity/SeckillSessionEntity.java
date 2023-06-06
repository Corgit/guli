package com.guli.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 秒杀活动场次
 * 
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:06:36
 */
@Data
@TableName("sms_seckill_session")
public class SeckillSessionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 场次名称
	 */
	@ApiModelProperty("场次名称")
	private String name;
	/**
	 * 每日开始时间
	 */
	@ApiModelProperty("秒杀开始时间")
	private Date startTime;
	/**
	 * 每日结束时间
	 */
	@ApiModelProperty("秒杀结束时间")
	private Date endTime;
	/**
	 * 启用状态
	 */
	@ApiModelProperty("启用状态")
	private Integer status;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(hidden = true)
	private Date createTime;

	/**
	 * 是否有效
	 */
	@ApiModelProperty(hidden = true)
	private Integer valid;

}
