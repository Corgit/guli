package com.guli.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.product.entity.SpuInfoEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: Ryan_Wuyx
 * @Date: 2023/10/12 10:22
 */
public interface OfficeService extends IService<SpuInfoEntity> {

    void uploadSpuInfoByExcel();

    void downloadSpu(HttpServletResponse response);
}
