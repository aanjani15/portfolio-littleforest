package com.fintecho.littleforest.service;

import java.util.ArrayList;

import com.fintecho.littleforest.vo.LikesVO;

public interface LikesService {

	ArrayList<Integer> checklikes(int user_Id);
}
