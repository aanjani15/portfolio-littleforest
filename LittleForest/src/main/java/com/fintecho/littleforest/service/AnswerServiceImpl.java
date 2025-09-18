package com.fintecho.littleforest.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.AnswerMapper;
import com.fintecho.littleforest.vo.AnswerVO;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerMapper answerMapper;

	@Override
	public int countAnswer(int inquery_Id) {
		// TODO Auto-generated method stub
		return answerMapper.countAnswer(inquery_Id);
	}

	@Override
	public ArrayList<AnswerVO> getAnswer(int inquery_Id) {
		// TODO Auto-generated method stub
		return answerMapper.getAnswer(inquery_Id);
	}

	@Override
	public int insertAnswer(AnswerVO avo) {
		// TODO Auto-generated method stub
		return answerMapper.insertAnswer(avo);
	}
	
	
}
