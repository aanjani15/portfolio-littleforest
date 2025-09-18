package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class DonationVO {
    private int id;
    private int user_Id;
    private int amount;
    private Date donation_Date;
    private String description;

}
