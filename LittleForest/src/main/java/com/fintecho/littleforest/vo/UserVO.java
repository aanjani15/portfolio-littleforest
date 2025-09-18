package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class UserVO {
	private int id; // PK
	private String oauth_Id; // 소셜 로그인 ID
	private String password; // 비밀번호
	private String name; // 이름
	private String phone; // 전화번호
	private Date birth; // 생년월일 (문자열)
	private String role; // 권한
	private String badge; // 뱃지
	private Date created_At; // 가입일
	private String address; // 주소
	private String email; // 이메일
	private String nickname; // 닉네임
	private int point; // 포인트
	private String profile_Photo; // 프로필 사진
}
