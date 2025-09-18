package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class WalletVO {
    private int id;
    private int user_Id;
    private String bank_Name;
    private int account_Balance;
    private Date last_Update;
    private String account_Number;
    
    
}
