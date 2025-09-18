package com.fintecho.littleforest.vo;

import lombok.Data;

@Data
public class EmissionMonthlyVO {
    private int month; // 1~12
    private int kg;    // 월 합계
}