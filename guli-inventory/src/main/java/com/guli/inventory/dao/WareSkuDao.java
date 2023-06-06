package com.guli.inventory.dao;

import com.guli.inventory.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:09:03
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
