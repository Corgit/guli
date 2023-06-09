package com.guli.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.common.utils.PageUtils;
import com.guli.product.entity.SkuInfoEntity;
import com.guli.product.vo.AddressVo;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 11:56:52
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    SkuInfoEntity querySkuInfoById(Long id);

    void esHistoryUpdate(List<AddressVo> vos);

    void toEsHistory();
}

