package com.guli.product.dao;

import com.guli.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 11:56:52
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
