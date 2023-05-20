package com.guli.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.coupon.dao.SeckillSkuRelationDao;
import com.guli.coupon.entity.SeckillSkuRelationEntity;
import com.guli.coupon.service.SeckillSkuRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/15 22:47
 */
@Service
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity> implements SeckillSkuRelationService {

    @Override
    public Boolean delSession(List<Long> ids) {
        if(!ids.isEmpty()) {
            UpdateWrapper<SeckillSkuRelationEntity> wrapper = new UpdateWrapper<>();
            wrapper.in("promotion_session_id", ids);
            return this.remove(wrapper);
        }
        return false;
    }

    @Override
    public Boolean insertRelation(List<SeckillSkuRelationEntity> list) {
        if(!list.isEmpty()) {
            return this.saveBatch(list);
        }
        return false;
    }

}
