package com.fintecho.littleforest.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.vo.CommentVO;
import com.fintecho.littleforest.vo.CommunityVO;
import com.fintecho.littleforest.vo.admin.AdminVO;

@Mapper
public interface AdminPostMapper {
	int countPosts(AdminVO vo);

	List<Map<String, Object>> findPosts(AdminVO vo);

	int bulkDeleteWeirdTypes(@Param("allowedTypes") List<String> allowedTypes);

	CommunityVO findPostById(@Param("id") int id);

	List<CommentVO> findCommentsByCommunityId(@Param("id") int id);

	void deletePost(int id);

	void deleteComment(int id);
}
