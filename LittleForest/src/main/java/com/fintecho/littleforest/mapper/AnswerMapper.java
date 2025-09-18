package com.fintecho.littleforest.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.fintecho.littleforest.vo.AnswerVO;

@Mapper
public interface AnswerMapper {
int countAnswer(@Param("inquery_Id") int inquery_Id);
ArrayList<AnswerVO> getAnswer(@Param("inquery_Id") int inquery_Id);
int insertAnswer(AnswerVO avo);
}
