package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class GrownTreeVO {

	private int id;
	private int user_Id;
	private int growing_Tree_Id;
	private Date complete_Tree;
	private String ident_Tree;
	private String photo;
}
