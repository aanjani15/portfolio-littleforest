package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CommentVO {
	private int id;
	private int user_Id;
	private int community_Id;
	private String comment_Id;
	private Date created_At;
	private int likes;

	private com.fintecho.littleforest.vo.UserVO user;
}
