package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class RandomTreeVO {
private int id;
private int growing_Tree_Id;
private String event_Name;
private Date event_Happen;
private int event_Max;
private int event_Success;
}
