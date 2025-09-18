package com.fintecho.littleforest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.vo.PointVO;

@Mapper
public interface PointMapper {
	int randomcost(PointVO pvo);

	int biryocost(PointVO pvo);

// 포인트 내역들 불러오기
	List<PointVO> getPointByUserId(int userId);

// 공통 로그 INSERT (원하는 곳에서 직접 타입/부호 지정해서 쓰고 싶을 때)
	int insertLog(int userId, int amount, // 양수/음수 그대로 기록
			String type, // 'EARN','SPEND','CHARGE','DONATE','GIFT'
			Integer counterpartyUserId, String memo);

// 적립/충전/사용/기부
	int insertEarn(int userId, int amount, String memo);

	int insertCharge(int userId, int amount, String memo);

	int insertSpend(int userId, int amount, String memo);

	int insertDonate(@Param("user_Id") int user_Id, @Param("amount") int amount, @Param("memo") String memo);

// 선물 (보낸쪽/받은쪽 각각 기록)
	int insertGiftOut(int fromUserId, int toUserId, int amount, String memo);

	int insertGiftIn(int toUserId, int fromUserId, int amount, String memo);

}
