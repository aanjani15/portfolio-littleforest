package com.fintecho.littleforest.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.fintecho.littleforest.vo.LikesVO;

@Mapper
public interface LikesMapper {
//추가
void likesInsert(LikesVO lvo);
//삭제
void likesDelete(LikesVO lvo);
//좋아요를 누른 기록 조회
int iflikes(LikesVO lvo);
//커뮤니티 삭제할때 같이 삭제되어야 할것
void likesDeleteAll(LikesVO lvo);
//좋아요 체크용
ArrayList<Integer> checklikes(int user_Id);
}
