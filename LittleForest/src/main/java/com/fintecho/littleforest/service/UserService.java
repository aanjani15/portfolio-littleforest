package com.fintecho.littleforest.service;

import com.fintecho.littleforest.vo.UserVO;

public interface UserService {

	UserVO getinform(int user_Id);

// 닉네임으로 회원 조회 (로그인)
	UserVO findByOauthID(String oauth_id);

// 회원가입
	void joinUser(UserVO user);

// Oauth ID 중복 체크
	boolean isOauthIdDuplicate(String oauthId);

	UserVO getInfo(int id);

	// 포인트 선물하기
	boolean existsById(int id);

	int userUpdate(UserVO uvo);

	// 회원정보 수정(프로필만)
	int userUpdateProfile(UserVO uvo);

	int nicknameChecking(String nickname);

	void adminInform(String role);

	// 비밀번호 수정을 위한것
	int userUpdatePassword(UserVO uvo);

}
