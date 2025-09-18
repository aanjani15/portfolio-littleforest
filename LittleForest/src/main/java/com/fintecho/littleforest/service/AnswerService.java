package com.fintecho.littleforest.service;

import java.util.ArrayList;

import com.fintecho.littleforest.vo.AnswerVO;

public interface AnswerService {
	int countAnswer(int inquery_Id);

	ArrayList<AnswerVO> getAnswer(int inquery_Id);

	int insertAnswer(AnswerVO avo);
}
