package com.guli.orders.dao;

import com.guli.orders.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:01:44
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
