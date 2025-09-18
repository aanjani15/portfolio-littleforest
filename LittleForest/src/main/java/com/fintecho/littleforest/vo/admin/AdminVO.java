package com.fintecho.littleforest.vo.admin;

import lombok.Data;

@Data
public class AdminVO {

	private String keyword;

	private String role;

	private String type;

	private int page = 1;
	private int size = 20;

	public int getOffset() {
		return (page - 1) * size;
	}
}
