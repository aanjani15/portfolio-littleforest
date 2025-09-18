package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class InqueryVO {
	private int id;
	private int user_Id;
	private String category;
	private String title;
	private String content;
	private Date created_At;
	private String status;
    //조인용
	private String nickname;
	private String oauth_Id;
}
