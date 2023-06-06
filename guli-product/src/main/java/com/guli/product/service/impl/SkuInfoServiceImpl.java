package com.guli.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.guli.product.vo.AddressVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.utils.PageUtils;
import com.guli.common.utils.Query;

import com.guli.product.dao.SkuInfoDao;
import com.guli.product.entity.SkuInfoEntity;
import com.guli.product.service.SkuInfoService;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public SkuInfoEntity querySkuInfoById(Long id) {
        LambdaQueryWrapper<SkuInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuInfoEntity::getSkuId, id);
        return this.getOne(wrapper);
    }

    @Override
    public void esHistoryUpdate(List<AddressVo> vos) {
        if(vos != null && vos.size() > 0) {
            List<SkuInfoEntity> list = vos.stream().map(
                    v -> {
                        SkuInfoEntity entity = new SkuInfoEntity();
                        entity.setSkuId(Long.valueOf(v.getId()));
                        entity.setCatalogId(v.getSize().longValue());
                        entity.setSkuTitle(v.getRole());
                        entity.setSkuSubtitle(v.getName());
                        entity.setSkuDesc(v.getAddress());
                        return entity;
                    }
            ).collect(Collectors.toList());
            this.saveBatch(list);
        }
    }
}