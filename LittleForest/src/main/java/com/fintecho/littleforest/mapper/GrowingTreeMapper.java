package com.fintecho.littleforest.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.vo.GrowingTreeVO;

@Mapper
public interface GrowingTreeMapper {
//사용자가 보유한 나무의 전체 컬럼 내역 조회
	GrowingTreeVO getAllStock(@Param("user_Id") int user_Id);

//사용자가 키우고 있는 나무 재고 조회 선언
	int ifTree(int user_Id);

//나무를 심는다(사용자가 키우는 나무가 없다면)
	int insertTree(GrowingTreeVO treevo);

//유저가 비료 구매하는 것을 db에 업데이트
	void updatestock(@Param("quantity") int quantity, @Param("user_Id") int user_Id);

//물 재고 증가 (시간이 지나면)
	void updatews(@Param("user_Id") int user_Id);

//물주기 버튼(water_stock -1, water_count +1, last_update (sysdate 버튼 누른 시간 기준)
	void updatewater(@Param("user_Id") int user_Id);

//레벨증가(경험치에 비례해서)
	void updatelevel(@Param("tree_Level") int tree_Level, @Param("user_Id") int user_Id);

//비료주기 활성화 (biyro_stock -1, biyro_used_at=sysdate)
	void updatebiyro(@Param("biyro_Stock") int biyro_Stock, @Param("user_Id") int user_Id);

//비료를 준지 72시간이 지나지 않았으면 경험치 두배 증가 (water_count+2)
	void updateburning(@Param("user_Id") int user_Id);

//레벨7 달성하고 다시키우기 선택시 삭제
	void deletetree(GrowingTreeVO deletevo);

	// 키우던 나무에서 재고가 있으면 재고테이블에서 업데이트(트랜잭션 예정)
	int insertStockByUserId(GrowingTreeVO treevo);

}
