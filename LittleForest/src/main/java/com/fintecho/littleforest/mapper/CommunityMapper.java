package com.fintecho.littleforest.mapper;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.fintecho.littleforest.vo.CommunityVO;

@Mapper
public interface CommunityMapper {

// 커뮤니티 데이터 전체 조회(최신순)
ArrayList<CommunityVO> selectCommunity();
// 커뮤니티 데이터 전체 조회(오래된순)
ArrayList<CommunityVO> selectCommunityasc();
//본인의 커뮤니티 조회
ArrayList<CommunityVO> selectUserCommunity(@Param("user_Id") int user_Id);
//인기순 커뮤니티 데이터 조회(인기순)
ArrayList<CommunityVO> selectlikesCommunity();
//커뮤니티 원하는 타입 선택(자유, 정보공유, 공지 이렇게 3개로 지정하였으나 추가가능)
ArrayList<CommunityVO> selectTypeCommunity(Map<String,Object> params);
//커뮤니티 하나 선택(커뮤니티 아무거나 하나 선택)
CommunityVO selectOneCommunity(@Param("id") int id);
//본인의 커뮤니티 하나 선택
CommunityVO selectOwnCommunity(@Param("id") int id, @Param("user_Id") int user_Id);
//커뮤니티 등록
void insertCommunity(CommunityVO vo);
//커뮤니티 수정
void updateCommunity(CommunityVO vo);
//커뮤니티 삭제
void deleteCommunity(CommunityVO vo);
//커뮤니티 좋아요 추가
int updatelikesplus(CommunityVO vo);
//커뮤니티 좋아요 제거
int updatelikesminus(CommunityVO vo);
//커뮤니티 최종 좋아요 숫자 출력을 위함
int finallyTest(@Param("id") int id);
//관리자 삭제용
void deleteAdmin(CommunityVO vo);

}
