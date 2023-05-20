package com.guli.coupon.ao;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @describe：查询秒杀场次信息参数
 * @author: Ryan_Wu
 * @Date: 2022/3/16 9:59
 */
@Data
public class SeckillQueryAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("秒杀场次id")
    private Long id;

    @ApiModelProperty("秒杀场次id")
    private Long promotionSessionId;

    @ApiModelProperty("秒杀场次名")
    private String name;

    @ApiModelProperty("秒杀活动开始时间")
    private Date startTime;

    @ApiModelProperty("秒杀活动结束时间")
    private Date endTime;

    @ApiModelProperty("秒杀活动状态")
    private Integer status;

    @ApiModelProperty("秒杀商品id")
    private String skuId;

    @ApiModelProperty("当前页码")
    private Integer currentPage;

    @ApiModelProperty("页面大小")
    private Integer pageSize;

}
