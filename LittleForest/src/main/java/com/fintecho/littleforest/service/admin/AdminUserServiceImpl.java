package com.fintecho.littleforest.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.admin.AdminUserMapper;
import com.fintecho.littleforest.vo.UserVO;

@Service
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserMapper adminUserMapper;

	@Override
	public UserDetails loadUserByUsername(String idStr) throws UsernameNotFoundException {
		int id;
		try {
			id = Integer.parseInt(idStr.trim());
		} catch (NumberFormatException e) {
			throw new UsernameNotFoundException("ID는 숫자여야 합니다.");
		}

		// role='admin'인 사용자만 통과
		UserVO u = adminUserMapper.findAdminById(id);
		if (u == null) {
			throw new UsernameNotFoundException("관리자 계정이 없습니다.");
		}

		return new org.springframework.security.core.userdetails.User(String.valueOf(u.getId()), u.getPassword(), // BCrypt
																													// 해시
				List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
	}
}
