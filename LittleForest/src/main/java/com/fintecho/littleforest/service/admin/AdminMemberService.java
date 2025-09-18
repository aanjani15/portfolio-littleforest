package com.fintecho.littleforest.service.admin;

import java.util.Map;

import com.fintecho.littleforest.vo.UserVO;
import com.fintecho.littleforest.vo.admin.AdminVO;

public interface AdminMemberService {

	Map<String, Object> list(AdminVO vo);

	void changeRole(int id, String role);

	UserVO findById(int id); 

	int updateBasic(UserVO vo);
}
