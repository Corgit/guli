package com.guli.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.coupon.entity.SeckillSkuRelationEntity;

import java.util.List;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/15 22:46
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    Boolean delSession(List<Long> ids);

    Boolean insertRelation(List<SeckillSkuRelationEntity> list);

}
