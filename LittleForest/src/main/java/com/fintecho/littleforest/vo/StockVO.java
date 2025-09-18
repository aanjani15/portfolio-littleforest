package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class StockVO {
	private int id;
	private int user_Id;
	private int biyro_Stock;
	private int water_Stock;
	private Date biyro_Used_At;
	private Date save_Date;
	private Date update_Date;
}
