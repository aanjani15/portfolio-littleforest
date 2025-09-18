package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PointEventVO {
	
	private int id;
	private int user_Id;
    private String event_Code;
    private Date claim_Date;
    private Date claimed_At;
    private int amount;
    private String memo;	

}
