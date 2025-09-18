package com.fintecho.littleforest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.vo.UserVO;

@Mapper
public interface UserMapper {
//해당 사원번호의 컬럼 전체 조회
	UserVO getinfrom(int user_Id);

//비료구매할때 포인트 차감용 업데이트
	void updatepoint(@Param("totalprice") int totalprice, @Param("user_Id") int user_Id);

	void update_100(@Param("user_Id") int user_Id);

//로그인 //닉네임으로 사용자 한 명 정보 가져오기
	UserVO findByOauthID(@Param("oauth_id") String oauth_id);

	int checkEmailAndIdDupl(String id, String email);

	// 회원가입
	void insertUser(UserVO user);

	UserVO getInfo(int id); // 회원정보 조회
	// 포인트 락 조회

	Integer selectPointForUpdate(int id);

	// 포인트 차감(보유 초과 방지)
	int deductPoint(int id, int amount);

	// 포인트 증가(추가)
	int addPoint(int id, int amount);

	// 포인트 선물하기 추가
	int countById(@Param("id") int id);

	// (getBalance 구현 시 필요)
	Integer selectPoint(@Param("userId") int userId);

	/*
	 * UserVO getInfo(@Param("id") int id); Integer
	 * selectPointForUpdate(@Param("id") int id); int deductPoint(@Param("id") int
	 * id, @Param("amount") int amount);
	 */

	int userUpdate(UserVO uvo);

	// 회원정보 수정(프로필만)
	int userUpdateProfile(UserVO uvo);

	// 닉네임 중복체크용
	int nicknameChecking(@Param("nickname") String nickname);

	// 관리자 이메일만 가져오기
	void adminInform(@Param("role") String role);

	// 비밀번호 수정을 위한것
	int userUpdatePassword(UserVO uvo);
	
	//
	int getUserseqByEmail(String email);

}
