package com.guli.seckill.vo;

import com.guli.seckill.dto.SkuRelationInfoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/20 15:50
 */
@Data
public class SeckillInfoRedisVO {

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
     * 商品详细信息，字段名需要和优惠服务相同
     */
    private List<SkuRelationInfoDTO> relatedSkus;

}
