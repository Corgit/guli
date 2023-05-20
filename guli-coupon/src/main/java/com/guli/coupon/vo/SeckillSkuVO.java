package com.guli.coupon.vo;

import com.guli.coupon.entity.SeckillSkuRelationEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/20 10:53
 */
@Data
public class SeckillSkuVO {

    /**
     * 秒杀场次id
     */
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
     * 当场秒杀活动关联的商品
     */
    private List<SeckillSkuRelationEntity> relatedSkus;

}
