package com.guli.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.common.utils.PageUtils;
import com.guli.inventory.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:09:03
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

