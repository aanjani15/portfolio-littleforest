package com.fintecho.littleforest.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.InqueryMapper;
import com.fintecho.littleforest.vo.InqueryVO;

@Service
public class InqueryServiceImpl implements InqueryService {

	@Autowired
	private InqueryMapper inqueryMapper;
	@Override
	public ArrayList<InqueryVO> getAllInquery() {
		// TODO Auto-generated method stub
		return inqueryMapper.getAllInquery();
	}

	@Override
	public ArrayList<InqueryVO> getUserInquery(int user_Id) {
		// TODO Auto-generated method stub
		return inqueryMapper.getUserInquery(user_Id);
	}

	@Override
	public InqueryVO getOneInquery(int id,int user_Id) {
		// TODO Auto-generated method stub
		return inqueryMapper.getOneInquery(id,user_Id);
	}

	@Override
	public int insertInquery(InqueryVO ivo) {
		// TODO Auto-generated method stub
		return inqueryMapper.insertInquery(ivo);
	}

	@Override
	public int updateInquery(InqueryVO ivo) {
		// TODO Auto-generated method stub
		return inqueryMapper.updateInquery(ivo);
	}

	@Override
	public int deleteInquery(InqueryVO ivo) {
		// TODO Auto-generated method stub
		return inqueryMapper.deleteInquery(ivo);
	}

	@Override
	public int updateStatus(InqueryVO ivo) {
		// TODO Auto-generated method stub
		return inqueryMapper.updateStatus(ivo);
	}

	@Override
	public InqueryVO inqueryAnswer(int user_Id) {
		// TODO Auto-generated method stub
		return inqueryMapper.inqueryAnswer(user_Id);
	}

}
