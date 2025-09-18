package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class AnswerVO {
	private int id;
	private int user_Id;
	private int inquery_Id;
	private int answer_Id;
	private String content;
	private Date created_At;
//조인용
	private String nickname;
}
