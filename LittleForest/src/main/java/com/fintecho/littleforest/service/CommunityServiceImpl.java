package com.fintecho.littleforest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fintecho.littleforest.mapper.CommentMapper;
import com.fintecho.littleforest.mapper.CommunityMapper;
import com.fintecho.littleforest.mapper.LikesMapper;
import com.fintecho.littleforest.vo.CommentVO;
import com.fintecho.littleforest.vo.CommunityVO;
import com.fintecho.littleforest.vo.LikesVO;

@Service
public class CommunityServiceImpl implements CommunityService {
	
	@Autowired
	private CommunityMapper communityMapper;
	
	@Autowired
	private LikesMapper likesMapper;
	
	@Autowired
	private CommentMapper commentMapper;
	@Override
	public ArrayList<CommunityVO> selectCommunity() {
		// TODO Auto-generated method stub
		return communityMapper.selectCommunity();
	}

	@Override
	public ArrayList<CommunityVO> selectUserCommunity(int user_Id) {
		// TODO Auto-generated method stub
		return communityMapper.selectUserCommunity(user_Id);
	}

	@Override
	public ArrayList<CommunityVO> selectlikesCommunity() {
		// TODO Auto-generated method stub
		return communityMapper.selectlikesCommunity();
	}

	@Override
	public CommunityVO selectOneCommunity(int id) {
		// TODO Auto-generated method stub
		return communityMapper.selectOneCommunity(id);
	}

	@Override
	public CommunityVO selectOwnCommunity(int id, int user_Id) {
		// TODO Auto-generated method stub
		return communityMapper.selectOwnCommunity(id, user_Id);
	}

	@Override
	public ArrayList<CommunityVO> selectCommunityasc() {
		// TODO Auto-generated method stub
		return communityMapper.selectCommunityasc();
	}

	public void insertCommunity(CommunityVO vo) {
	    communityMapper.insertCommunity(vo);
	}
	@Override
	public void updateCommunity(CommunityVO vo) {
		communityMapper.updateCommunity(vo);
		
	}

	@Transactional
	public void deleteCommunity(CommunityVO vo) {
		LikesVO lvo=new LikesVO();
		CommentVO cvo=new CommentVO();
		cvo.setCommunity_Id(vo.getId());
		if(vo.getTitle() != null &&!vo.getTitle().isEmpty()) {
			lvo.setTable_Type("community");
			lvo.setCommunity_Id(vo.getId());
		}
		likesMapper.likesDeleteAll(lvo);
		commentMapper.deleteCommentAll(cvo);
		communityMapper.deleteCommunity(vo);
		
	}

	@Transactional
	public int updatelikesplus(CommunityVO vo) {
		LikesVO lvo=new LikesVO();
		lvo.setUser_Id(vo.getUser_Id());
		
		if(!vo.getTitle().isEmpty()) {
			lvo.setTable_Type("community");
			lvo.setCommunity_Id(vo.getId());
		}else {
			lvo.setTable_Type("comment");
		}
		int count=likesMapper.iflikes(lvo);
		if(count==0) {
			likesMapper.likesInsert(lvo);
			communityMapper.updatelikesplus(vo);	
		}else if(count==1){
			likesMapper.likesDelete(lvo);
			communityMapper.updatelikesminus(vo);
		}
		return communityMapper.finallyTest(vo.getId());
		
	}

	@Override
	public ArrayList<CommunityVO> selectTypeCommunity(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return communityMapper.selectTypeCommunity(params);
	}

	@Transactional
	public void deleteAdmin(CommunityVO vo) {
		LikesVO lvo=new LikesVO();
		CommentVO cvo=new CommentVO();
		cvo.setCommunity_Id(vo.getId());
		if(vo.getTitle() != null &&!vo.getTitle().isEmpty()) {
			lvo.setTable_Type("community");
			lvo.setCommunity_Id(vo.getId());
		}
		likesMapper.likesDeleteAll(lvo);
		commentMapper.deleteCommentAll(cvo);
		communityMapper.deleteAdmin(vo);
		
	}

}
