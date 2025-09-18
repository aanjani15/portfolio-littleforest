package com.fintecho.littleforest.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.vo.CommentVO;

@Mapper
public interface CommentMapper {
//댓글 전체 조회
ArrayList<CommentVO> selectComment(int community_Id);

//사용자 댓글 전체조회
ArrayList<CommentVO> selectUserComment(int user_Id);

//댓글 개수만 조회 (댓글보기 none/block 여부 활용)
int countComment(int community_Id);

//커뮤니티 댓글 등록
void insertComment(CommentVO vo);

//커뮤니티 댓글 수정
void updateComment(CommentVO vo);
//커뮤니티 댓글 하나만 선택
CommentVO selectOneComment(@Param("id") int id);
//커뮤니티 댓글 삭제
void deleteComment(CommentVO vo);

void deleteadmin2(CommentVO vo);

int updatelikesplus2(CommentVO vo);

int updatelikesminus2(CommentVO vo);

void deleteCommentAll(CommentVO cvo);

int total(int id);
}
