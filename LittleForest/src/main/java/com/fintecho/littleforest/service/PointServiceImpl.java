package com.fintecho.littleforest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintecho.littleforest.mapper.PaymentMapper;
import com.fintecho.littleforest.mapper.PointMapper;
import com.fintecho.littleforest.mapper.UserMapper;
import com.fintecho.littleforest.service.PortOneClient.PortOnePayment;
import com.fintecho.littleforest.vo.PointVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

	@Autowired
	private PointMapper pointMapper;
	@Autowired
	private UserMapper userMapper;

	private final PaymentMapper paymentMapper; // 결제/적립 기록 남길 때 사용(선택)
	private final PortOneClient portOneClient; // 포트원 REST 호출 유틸(아래 6) 참고)

	@Transactional
	public int randomcost(PointVO pvo) {
		int user_Id = 4;
		userMapper.update_100(user_Id);
		return pointMapper.randomcost(pvo);
	}

	/** 카카오페이 결제 검증 + 포인트 적립 */
	@Override
	@Transactional
	public int verifyAndChargeByKakaoPay(int userId, String impUid, String merchantUid, int amount) {
		if (amount < 1000)
			throw new IllegalArgumentException("MIN_AMOUNT");

		// 1) 포트원 REST API로 결제내역 조회 금액/상태 검증
		PortOnePayment paid = portOneClient.getPayment(impUid);
		if (!"paid".equalsIgnoreCase(paid.getStatus())) {
			throw new IllegalStateException("NOT_PAID");
		}
		if (paid.getAmount() != amount) {
			// 결제창 조작 등 금액 불일치 보호
			throw new IllegalStateException("AMOUNT_MISMATCH");
		}

		// 2) 포인트 적립(동시성 안전 X → 잠금 조회 후 addPoint)
		// 충전은 차감 조건이 없으니 selectForUpdate 없이도 OK지만,
		// 일관성을 위해 잠금 조회 사용해도 무방
		Integer cur = userMapper.selectPointForUpdate(userId);
		if (cur == null)
			throw new IllegalStateException("NO_USER");

		userMapper.addPoint(userId, amount);

		pointMapper.insertCharge(userId, amount, "카카오페이 충전");
		// (선택) 히스토리/영수증성 로그가 필요하다면 payment_table 등에 기록
		// 테이블 스키마상 wallet_id NOT NULL이면 생략해도 됨
		// 아래는 예시: type=deposit, description='포인트 충전(카카오페이)'

		return cur + amount;
	}

	/** 즉시 적립(이벤트) */
	@Override
	@Transactional
	public int earn(int userId, int amount, String memo) {
		if (amount <= 0)
			throw new IllegalArgumentException("INVALID_AMOUNT");
		Integer cur = userMapper.selectPointForUpdate(userId);
		if (cur == null)
			throw new IllegalStateException("NO_USER");

		userMapper.addPoint(userId, amount);
		pointMapper.insertEarn(userId, amount, memo);
		return cur + amount;
	}

	/** 포인트 선물: 보내는 사람 차감 + 받는 사람 증가 (동시성 안전) */
	@Override
	@Transactional
	public int gift(int fromUserId, int toUserId, int amount, String memo) {
		if (fromUserId == toUserId)
			throw new IllegalArgumentException("SAME_USER");
		if (amount <= 0)
			throw new IllegalArgumentException("INVALID_AMOUNT");

		// 교착상태 방지를 위해 id 작은 쪽 → 큰 쪽 순서로 잠금
		int first = Math.min(fromUserId, toUserId);
		int second = Math.max(fromUserId, toUserId);

		Integer p1 = userMapper.selectPointForUpdate(first);
		Integer p2 = userMapper.selectPointForUpdate(second);

		// 다시 실제 주체로 꺼내기
		Integer fromCur = (first == fromUserId) ? p1 : p2;
		Integer toCur = (first == toUserId) ? p1 : p2;

		if (fromCur == null || toCur == null)
			throw new IllegalStateException("NO_USER");

		if (fromCur < amount)
			throw new IllegalArgumentException("INSUFFICIENT");
		// 보내는 사람 차감(조건부)
		int updated = userMapper.deductPoint(fromUserId, amount);
		if (updated == 0)
			throw new IllegalArgumentException("INSUFFICIENT");
		// 보낸 사람 로그(음수)
		pointMapper.insertGiftOut(fromUserId, toUserId, amount, memo);
		// 받는 사람 증가
		userMapper.addPoint(toUserId, amount);
		// 받는 사람 로그(양수)
		pointMapper.insertGiftIn(toUserId, fromUserId, amount, memo);
		return (fromCur - amount); // 보낸 사람의 새 잔액 반환
	}

	// 포인트 선물하기 (없는 유저 등..)
	@Override
	public boolean userExists(int userId) {
		return userMapper.countById(userId) > 0;
	}

	@Override
	public int getBalance(int userId) {
		// 락이 필요 없는 단순 조회용
		Integer p = userMapper.selectPoint(userId); // 없으면 0 또는 null 처리
		if (p == null)
			throw new IllegalStateException("NO_USER");
		return p;
	}

	@Override
	public List<PointVO> getPointByUserId(int userId) {
		return pointMapper.getPointByUserId(userId);
	}

}
