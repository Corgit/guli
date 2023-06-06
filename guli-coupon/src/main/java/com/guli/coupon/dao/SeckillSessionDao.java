package com.guli.coupon.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.coupon.ao.SeckillQueryAO;
import com.guli.coupon.entity.SeckillSessionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.coupon.vo.SeckillSkuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 秒杀活动场次
 * 
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:06:36
 */
@Mapper
public interface SeckillSessionDao extends BaseMapper<SeckillSessionEntity> {

    /**
     * 分页要起作用，需要在方法中传入page参数，并且配置拦截器
     */
    @Select("select * from sms_seckill_session where valid = 1 and id = #{ao.id}\n" +
            "and status = #{ao.status}\n" +
            "and start_time >= #{ao.startTime}\n" +
            "and end_time <= #{ao.endTime}\n" +
            "and name like concat('%', #{ao.name}, '%')")
    Page<SeckillSessionEntity> querySeckillSession(@Param("ao") SeckillQueryAO ao, Page<SeckillSessionEntity> page);

    /**
     * 查询指定日期区间的秒杀活动详情
     */
    @Select("select a.*, b.sku_id, b.seckill_price, b.seckill_count, b.seckill_limit, b.seckill_sort\n" +
            "from sms_seckill_session a\n" +
            "inner join sms_seckill_sku_relation b on a.id = b.promotion_session_id\n" +
            "where a.valid = 1 and b.valid = 1 and a.status = 1\n" +
            "and a.start_time >= #{startTime} and a.start_time <= #{endTime}")
    List<SeckillSkuVO> querySeckillDetail(@Param("startTime") String startTime, @Param("endTime") String endTime);

}
