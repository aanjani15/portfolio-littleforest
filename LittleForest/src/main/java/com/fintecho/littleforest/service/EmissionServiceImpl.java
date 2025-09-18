package com.fintecho.littleforest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.EmissionMapper;
import com.fintecho.littleforest.vo.EmissionMonthlyVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmissionServiceImpl implements EmissionService {

    private final EmissionMapper emissionMapper;

    @Override
    public List<EmissionMonthlyVO> getMonthlyEmissions(int user_Id, int year) {
        // 1~12월 모두 채워서 반환(없는 달은 0) → 차트 축 고정
        Map<Integer,Integer> map = emissionMapper.getMonthlyEmissions(user_Id, year)
            .stream().collect(Collectors.toMap(EmissionMonthlyVO::getMonth, EmissionMonthlyVO::getKg));

        List<EmissionMonthlyVO> list = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            EmissionMonthlyVO vo = new EmissionMonthlyVO();
            vo.setMonth(m);
            vo.setKg(map.getOrDefault(m, 0));
            list.add(vo);
        }
        return list;
    }

    @Override
    public int getMonthlyTotal(int user_Id, int year, int month) {
        Integer v = emissionMapper.getMonthlyTotal(user_Id, year, month);
        return (v == null ? 0 : v);
    }
}