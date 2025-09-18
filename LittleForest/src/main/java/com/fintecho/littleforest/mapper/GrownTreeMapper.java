package com.fintecho.littleforest.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.vo.GrownTreeVO;

@Mapper
public interface GrownTreeMapper {

//사용자가 키웠던 나무 전체조회
	ArrayList<GrownTreeVO> grownSelect(@Param("user_Id") int user_Id);

//다키운 나무는 나무역사로 이동
	void grownInsert(GrownTreeVO grownvo);

}
