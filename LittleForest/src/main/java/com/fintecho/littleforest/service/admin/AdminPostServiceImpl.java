package com.fintecho.littleforest.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintecho.littleforest.mapper.admin.AdminPostMapper;
import com.fintecho.littleforest.vo.CommentVO;
import com.fintecho.littleforest.vo.CommunityVO;
import com.fintecho.littleforest.vo.admin.AdminVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminPostServiceImpl implements AdminPostService {
	private final AdminPostMapper mapper;

	public Map<String, Object> list(AdminVO vo) {
		return Map.of("total", mapper.countPosts(vo), "rows", mapper.findPosts(vo));
	}

	public int bulkDeleteWeirdTypes(List<String> allowedTypes) {
		return mapper.bulkDeleteWeirdTypes(allowedTypes);
	}

	@Override
	public CommunityVO findPost(int community_Id) {
		return mapper.findPostById(community_Id);
	}

	@Override
	public List<CommentVO> findComments(int community_Id) {
		return mapper.findCommentsByCommunityId(community_Id);
	}
	
	@Override
	public void deletePost(int id) {
		mapper.deletePost(id);
	}
	
	@Override
	public void deleteComment(int id) {
		mapper.deleteComment(id);
	}
	
	

}
