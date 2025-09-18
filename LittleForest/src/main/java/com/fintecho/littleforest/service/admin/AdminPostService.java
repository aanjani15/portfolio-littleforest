package com.fintecho.littleforest.service.admin;

import java.util.List;
import java.util.Map;

import com.fintecho.littleforest.vo.CommentVO;
import com.fintecho.littleforest.vo.CommunityVO;
import com.fintecho.littleforest.vo.admin.AdminVO;

public interface AdminPostService {
	Map<String, Object> list(AdminVO vo);

	int bulkDeleteWeirdTypes(List<String> allowedTypes);

	CommunityVO findPost(int community_Id); 

	List<CommentVO> findComments(int community_Id);

	void deletePost(int id);
	
	void deleteComment(int id);
}
