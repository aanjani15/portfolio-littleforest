package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class EmissionVO {
	private int user_Id;          // DB: user_id
    private int emission;         // DB: emission (kg)
    private Date emission_Date;   // DB: e_date

}
