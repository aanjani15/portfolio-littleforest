package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PointVO {

	private int id;
	private int user_Id;
	private int amount;
	private String type;
	private int counterparty_User_Id;
	private String memo;
	private Date created_At;

	public enum PointType {
		EARN, // 적립
		SPEND, // 사용
		CHARGE, // 충전
		DONATE, // 기부
		GIFT // 선물(보내기/받기 모두 분류용)
	}
}
