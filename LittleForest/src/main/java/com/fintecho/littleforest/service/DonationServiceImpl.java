package com.fintecho.littleforest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintecho.littleforest.mapper.DonationMapper;
import com.fintecho.littleforest.mapper.PointMapper;
import com.fintecho.littleforest.mapper.UserMapper;
import com.fintecho.littleforest.vo.DonationVO;

import lombok.RequiredArgsConstructor;

	@Service
	@RequiredArgsConstructor
	public class DonationServiceImpl implements DonationService {

	    private final DonationMapper donationMapper;
	    private final UserMapper userMapper;
	    private final PointMapper pointMapper;
	    
	    @Override
	    @Transactional
	    public int donateByPoint(int userId, int amount, String description) {
	        if (amount < 1000) throw new IllegalArgumentException("MIN_AMOUNT");

	        // 현재 포인트 잠금 조회
	        Integer cur = userMapper.selectPointForUpdate(userId);
	        if (cur == null) throw new IllegalStateException("NO_POINT_ACCOUNT");
	        if (cur < amount) throw new IllegalArgumentException("INSUFFICIENT");

	        // 조건부 차감 (point >= amount 검증 포함)
	        int updated = userMapper.deductPoint(userId, amount);
	        if (updated == 0) throw new IllegalArgumentException("INSUFFICIENT");

	        // 기부 insert
	        DonationVO vo = new DonationVO();
	        vo.setUser_Id(userId);
	        vo.setAmount(amount);
			vo.setDescription(description == null ? "포인트 기부" : description); 
	        donationMapper.insertDonation(vo);

	        pointMapper.insertDonate(userId, amount, description); // 원장 기록(음수)        
	        // 새 잔액 반환
	        return cur - amount;
	    }
	}