package com.fintecho.littleforest.service.admin;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintecho.littleforest.mapper.admin.AdminMemberMapper;
import com.fintecho.littleforest.vo.UserVO;
import com.fintecho.littleforest.vo.admin.AdminVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminMemberServiceImpl implements AdminMemberService {
	private final AdminMemberMapper mapper;

	public Map<String, Object> list(AdminVO vo) {
		return Map.of("total", mapper.countMembers(vo), "rows", mapper.findMembers(vo));
	}

	public void changeRole(int id, String role) {
		mapper.updateRole(id, role);
	}

	@Override
	public UserVO findById(int id) {
		return mapper.findById(id);
	}

	@Override
	public int updateBasic(UserVO vo) {
		return mapper.updateBasic(vo);
	}
}
