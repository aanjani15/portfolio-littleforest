package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CommunityVO {

	private int id;
	private int user_Id;
	private String type;
	private String title;
	private String content;
	private Date created_At;
	private String photo;
	private int likes;
	private String nickname;
}
