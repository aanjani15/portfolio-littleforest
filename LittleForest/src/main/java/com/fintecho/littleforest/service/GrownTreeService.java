package com.fintecho.littleforest.service;

import java.util.ArrayList;

import com.fintecho.littleforest.vo.GrownTreeVO;

public interface GrownTreeService {
ArrayList<GrownTreeVO> grownSelect(int user_Id);

void grownInsert(GrownTreeVO grownvo);
}
