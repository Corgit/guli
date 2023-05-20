package com.guli.coupon.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.coupon.ao.SeckillQueryAO;
import com.guli.coupon.dao.SeckillSessionDao;
import com.guli.coupon.entity.SeckillSessionEntity;
import com.guli.coupon.entity.SeckillSkuRelationEntity;
import com.guli.coupon.service.SeckillSessionService;
import com.guli.coupon.service.SeckillSkuRelationService;
import com.guli.coupon.vo.SeckillSkuVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/15 15:41
 */
@Service
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {

    @Autowired
    private SeckillSkuRelationService relationService;

    @Override
    public Boolean insertSeckillSession(List<SeckillSessionEntity> list) {
        if(!list.isEmpty()) {
            list.stream().map(e -> {
                e.setCreateTime(new Date());
                return e;
            }).collect(Collectors.toList());
            return this.saveBatch(list);
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteSeckillSession(List<SeckillSessionEntity> list) {
        if(!list.isEmpty()) {
            List<Long> del = new ArrayList<>();
            for (SeckillSessionEntity entity : list) {
                del.add(entity.getId());
            }
            // remove方法当更新的记录数小于1时，会返回false
            Boolean flag1 = this.removeByIds(del);
            Boolean flag2 = relationService.delSession(del);
            return (flag1 && flag2);
        }
        return false;
    }

    /**
     * select * from sms_seckill_session where valid = 1
     * and id = #{id}
     * and name like %#{name}%
     * and start_time >= #{startTime}
     * and end_time <= #{endTime}
     * and status = #{status}
     * limit
     */
    @Override
    public Page<SeckillSessionEntity> querySeckillSessionInfo(SeckillQueryAO ao) {
        // 通过观察控制台生成的SQL语句是否有limit字句判断分页是否生效
        Page<SeckillSessionEntity> page = new Page<>(ao.getCurrentPage(), ao.getPageSize());
        LambdaQueryWrapper<SeckillSessionEntity> wrapper = new LambdaQueryWrapper<>();
        // 三种拼接SQL语句：lambda、原生SQL、使用注解
        boolean flag = ao.getId() != null;
        wrapper.eq(flag, SeckillSessionEntity::getId, ao.getId())
                .like(StringUtils.isNotEmpty(ao.getName()), SeckillSessionEntity::getName, ao.getName())
                .ge(ObjectUtil.isNotEmpty(ao.getStartTime()), SeckillSessionEntity::getStartTime, ao.getStartTime())
                .le(ObjectUtil.isNotEmpty(ao.getEndTime()), SeckillSessionEntity::getEndTime, ao.getEndTime())
                .eq(ObjectUtil.isNotEmpty(ao.getStatus()), SeckillSessionEntity::getStatus, ao.getStatus());
        return this.page(page, wrapper);
    }

    @Override
    public Page<SeckillSessionEntity> querySeckillSessionInfoWithAnnotation(SeckillQueryAO ao) {
        Page<SeckillSessionEntity> page = new Page<>(ao.getCurrentPage(), ao.getPageSize());
        return this.getBaseMapper().querySeckillSession(ao, page);
    }

    @Override
    public List<SeckillSkuVO> querySeckillDetail(String startTime, String endTime) {
        return this.getBaseMapper().querySeckillDetail(startTime, endTime);
    }

    @Override
    public List<SeckillSkuVO> querySeckillDetailTo(String startTime, String endTime) {
        LambdaQueryWrapper<SeckillSessionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(SeckillSessionEntity::getStartTime, startTime, endTime);
        List<SeckillSessionEntity> entityList = this.list(wrapper);
        if(entityList != null && entityList.size() > 0) {
            List<SeckillSkuVO> voList = new ArrayList<>();
            entityList.stream().peek(e->{
                SeckillSkuVO vo = new SeckillSkuVO();
                BeanUtil.copyProperties(e, vo);
                List<SeckillSkuRelationEntity> relaList = relationService.list(new LambdaQueryWrapper<SeckillSkuRelationEntity>()
                        .eq(SeckillSkuRelationEntity::getPromotionSessionId, e.getId()));
                vo.setRelatedSkus(relaList);
                voList.add(vo);
            }).collect(Collectors.toList());
            return voList;
        }
        return null;
    }

    @Override
    public SeckillSessionEntity getone() {
        return this.getById(1);
    }
}
