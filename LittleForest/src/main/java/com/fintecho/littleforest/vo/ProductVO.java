package com.fintecho.littleforest.vo;

import lombok.Data;

@Data
public class ProductVO {
	
	private int id;
	private int merchants_Id;
	private String name;
	private String category;
	private String image_Path;	
	private int price;
	private double carbon_Effect;
	private String description;
}