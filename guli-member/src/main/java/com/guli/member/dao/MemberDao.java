package com.guli.member.dao;

import com.guli.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:08:02
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
