package com.fintecho.littleforest.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.LikesMapper;
import com.fintecho.littleforest.vo.LikesVO;

@Service
public class LikesServiceImpl implements LikesService {

	@Autowired
	private LikesMapper likesMapper;

	@Override
	public ArrayList<Integer> checklikes(int user_Id) {
		// TODO Auto-generated method stub
		return likesMapper.checklikes(user_Id);
	}

}
