package com.fintecho.littleforest.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.vo.UserVO;
import com.fintecho.littleforest.vo.admin.AdminVO;

@Mapper
public interface AdminMemberMapper {

	int countMembers(AdminVO vo);

	List<Map<String, Object>> findMembers(AdminVO vo);

	int updateRole(@Param("id") int id, @Param("role") String role);

	UserVO findById(@Param("id") int id);

	int updateBasic(UserVO vo);
}
