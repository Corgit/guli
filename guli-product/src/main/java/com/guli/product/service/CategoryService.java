package com.guli.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.product.entity.CategoryEntity;

import java.util.List;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/10/24 15:13
 */
public interface CategoryService extends IService<CategoryEntity> {

    List<CategoryEntity> queryCategoryTree();
}
