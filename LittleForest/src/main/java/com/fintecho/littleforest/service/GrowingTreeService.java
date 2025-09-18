package com.fintecho.littleforest.service;
import com.fintecho.littleforest.vo.GrowingTreeVO;

public interface GrowingTreeService {
//사용자의 나무 조회(사실상 재고 조회용)
GrowingTreeVO getAllStock(int user_Id);

//사용자가 나무를 가지고 있는지 조회(count 활용)
boolean ifTree(int user_Id);

//사용자 나무 등록 (사용자가 키우는 나무가 없을때)
int insertTree(GrowingTreeVO treevo);

//사용자가 비료 구매시 업데이트(포인트가 있을시)
void updatestock(int quantity, int user_Id, int totalprice);

//사용자의 물 증가 (24시간마다 갱신)
void updatews(int user_Id);

//물주기 버튼(water_stock -1, water_count +1, last_update (sysdate 버튼 누른 시간 기준)
void updatewater(int user_Id);

//레벨증가
void updatelevel(int tree_Level, int user_Id);

//비료 주기
void updatebiyro(int biyro_Stock, int user_Id);

}
