package com.fintecho.littleforest.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fintecho.littleforest.vo.EmissionMonthlyVO;

@Mapper
public interface EmissionMapper {

    // 해당 연도 월별 합계(1~12)
    List<EmissionMonthlyVO> getMonthlyEmissions(int user_Id,
                                                int year);

    // 특정 연/월 총합 (KPI 큰 숫자)
    Integer getMonthlyTotal(int user_Id,
                            int year,
                            int month);
}