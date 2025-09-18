package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class GrowingTreeVO {
	private int id;
	private int user_Id;
	private int tree_Level;
	private String tree_Name;
	private float carbon_Saved;
	private int water_Count;
	private int water_Stock;
	private int biyro_Stock;
	private Date last_Updated;
	private Date biyro_Used_At;
}
