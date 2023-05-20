package com.guli.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.product.dao.CategoryDao;
import com.guli.product.entity.CategoryEntity;
import com.guli.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/10/24 15:14
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    /**
     * 查询分级目录树
     */
    @Override
    public List<CategoryEntity> queryCategoryTree() {

        // 查询所有目录列表
        List<CategoryEntity> catList = this.list();


        // 组装目录树


        return catList;
    }
}
