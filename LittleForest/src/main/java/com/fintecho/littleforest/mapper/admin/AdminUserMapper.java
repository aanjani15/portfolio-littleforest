package com.fintecho.littleforest.mapper.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.vo.UserVO;

@Mapper
public interface AdminUserMapper {
	UserVO findAdminById(@Param("id") int id);
}
