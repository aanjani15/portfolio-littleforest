package com.fintecho.littleforest.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.CommentMapper;
import com.fintecho.littleforest.mapper.LikesMapper;
import com.fintecho.littleforest.vo.CommentVO;
import com.fintecho.littleforest.vo.LikesVO;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private LikesMapper likesMapper;

	@Override
	public int countComment(int community_Id) {
		// TODO Auto-generated method stub
		return commentMapper.countComment(community_Id);
	}

	@Override
	public ArrayList<CommentVO> selectComment(int community_Id) {
		// TODO Auto-generated method stub
		return commentMapper.selectComment(community_Id);
	}

	@Override
	public void insertComment(CommentVO vo) {
		commentMapper.insertComment(vo);
		
	}
	public void updateComment(CommentVO vo) {
		commentMapper.updateComment(vo);
		
	}
	@Override
	public CommentVO selectOneComment(int id) {
		// TODO Auto-generated method stub
		return commentMapper.selectOneComment(id);
	}

	public void deleteComment(CommentVO vo) {
		commentMapper.deleteComment(vo);
		
	}

	@Override
	public void deleteadmin2(CommentVO vo) {
		commentMapper.deleteadmin2(vo);
		
	}

	@Override
	public int updatelikesplus2(CommentVO vo) {
		LikesVO lvo=new LikesVO();
		lvo.setUser_Id(vo.getUser_Id());
		if(!vo.getComment_Id().isEmpty()) {
			lvo.setTable_Type("comment");
			lvo.setComment_Id(vo.getId());
		}
		else {
			lvo.setTable_Type("community");
		}
		int count=likesMapper.iflikes(lvo);
		if(count==0) {
			commentMapper.updatelikesplus2(vo);
			likesMapper.likesInsert(lvo);
		}
		else if(count==1) {
			commentMapper.updatelikesminus2(vo);
			likesMapper.likesDelete(lvo);
		}
		
		return commentMapper.total(vo.getId());
	}

}
