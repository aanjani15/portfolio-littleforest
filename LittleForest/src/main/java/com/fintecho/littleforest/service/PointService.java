package com.fintecho.littleforest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fintecho.littleforest.vo.PointVO;

@Service
public interface PointService {
	int randomcost(PointVO pvo);

	int verifyAndChargeByKakaoPay(int userId, String impUid, String merchantUid, int amount);

	int earn(int userId, int amount, String memo);

	int gift(int fromUserId, int toUserId, int amount, String memo);

	// 포인트 선물하기
	boolean userExists(int userId);

	int getBalance(int userId);

// 포인트 내역 불러오기 
	List<PointVO> getPointByUserId(int userId);

}
