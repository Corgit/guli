package com.guli.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.common.utils.PageUtils;
import com.guli.inventory.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:09:03
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

