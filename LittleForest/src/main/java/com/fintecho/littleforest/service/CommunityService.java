package com.fintecho.littleforest.service;

import java.util.ArrayList;
import java.util.Map;
import com.fintecho.littleforest.vo.CommunityVO;

public interface CommunityService {
ArrayList<CommunityVO> selectCommunity();

ArrayList<CommunityVO> selectCommunityasc();

ArrayList<CommunityVO> selectUserCommunity(int user_Id);

ArrayList<CommunityVO> selectlikesCommunity();

CommunityVO selectOneCommunity(int id);

CommunityVO selectOwnCommunity(int id, int user_Id);

ArrayList<CommunityVO> selectTypeCommunity(Map<String,Object> params);

void insertCommunity(CommunityVO vo);
//커뮤니티 수정
void updateCommunity(CommunityVO vo);
//커뮤니티 삭제
void deleteCommunity(CommunityVO vo);

int updatelikesplus(CommunityVO vo);

void deleteAdmin(CommunityVO vo);
}
