package com.fintecho.littleforest.service;

import java.util.ArrayList;

import com.fintecho.littleforest.vo.CommentVO;

public interface CommentService {
	int countComment(int community_Id);
	
	ArrayList<CommentVO> selectComment(int community_Id);
	
	void insertComment(CommentVO vo);
	
	void updateComment(CommentVO vo);
	
	CommentVO selectOneComment(int id);
	
	void deleteComment(CommentVO vo);
	
	void deleteadmin2(CommentVO vo);
	
	int updatelikesplus2(CommentVO vo);
}
