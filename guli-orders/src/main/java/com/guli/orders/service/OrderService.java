package com.guli.orders.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.common.utils.PageUtils;
import com.guli.orders.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:01:44
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

