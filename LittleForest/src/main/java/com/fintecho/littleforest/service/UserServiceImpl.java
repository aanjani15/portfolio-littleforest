package com.fintecho.littleforest.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.UserMapper;
import com.fintecho.littleforest.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserVO getinform(int user_Id) {
		// TODO Auto-generated method stub
		return userMapper.getinfrom(user_Id);
	}

	@Override
	public void joinUser(UserVO user) {
		// 가입 시 기본값 세팅
		user.setRole("user");
		user.setCreated_At(new Date());
		user.setPoint(0);
		user.setBadge(null);
		user.setProfile_Photo(null);

		// DB 저장
		userMapper.insertUser(user);
	}

	@Override
	public UserVO findByOauthID(String oauth_id) {
		return userMapper.findByOauthID(oauth_id);
	}



	@Override
	public boolean isOauthIdDuplicate(String oauthId) {
		return userMapper.findByOauthID(oauthId) != null;
	}

	@Override
	public UserVO getInfo(int id) {
		return userMapper.getInfo(id);
	}

	// 포인트 선물하기 추가
	@Override
	public boolean existsById(int id) {
		return userMapper.countById(id) > 0;
	}

	@Override
	public int userUpdate(UserVO uvo) {
		// TODO Auto-generated method stub
		return userMapper.userUpdate(uvo);
	}

	@Override
	public int userUpdateProfile(UserVO uvo) {
		return userMapper.userUpdateProfile(uvo);
	}

	@Override
	public int nicknameChecking(String nickname) {
		return userMapper.nicknameChecking(nickname);
	}

	@Override
	public void adminInform(String role) {
		userMapper.adminInform(role);

	}

	@Override
	public int userUpdatePassword(UserVO uvo) {
		// TODO Auto-generated method stub
		return userMapper.userUpdatePassword(uvo);
	}

}
