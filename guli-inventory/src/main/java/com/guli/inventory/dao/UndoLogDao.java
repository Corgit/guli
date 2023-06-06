package com.guli.inventory.dao;

import com.guli.inventory.entity.UndoLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:09:03
 */
@Mapper
public interface UndoLogDao extends BaseMapper<UndoLogEntity> {
	
}
