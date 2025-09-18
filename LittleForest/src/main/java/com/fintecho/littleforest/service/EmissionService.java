package com.fintecho.littleforest.service;

import java.util.List;

import com.fintecho.littleforest.vo.EmissionMonthlyVO;

public interface EmissionService {
    List<EmissionMonthlyVO> getMonthlyEmissions(int user_Id, int year);
    int getMonthlyTotal(int user_Id, int year, int month);
}
