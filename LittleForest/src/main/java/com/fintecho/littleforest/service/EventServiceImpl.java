package com.fintecho.littleforest.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintecho.littleforest.mapper.PointEventMapper;
import com.fintecho.littleforest.mapper.PointMapper;
import com.fintecho.littleforest.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserMapper userMapper;
    private final PointEventMapper pointEventMapper;
    private final PointMapper pointMapper;

    @Override
    @Transactional
    public int claimDaily(int userId, String eventCode, int amount, String memo) {
        try {
            // 1) 오늘 첫 참여인지 INSERT로 판정(유니크 제약이 심판)
            pointEventMapper.insert(userId, eventCode, amount, memo);
        } catch (DataIntegrityViolationException e) { // ← 하나만 잡기
            // (유니크 위반 포함) 이미 오늘 참여
            throw new AlreadyClaimedTodayException();
        }

        //포인트 증가(락+ 증가 → 새 잔액 반환)        
        Integer cur = userMapper.selectPointForUpdate(userId);
        if (cur == null) throw new IllegalStateException("NO_USER");

        userMapper.addPoint(userId, amount);
        pointMapper.insertEarn(userId, amount, (memo != null ? memo : eventCode));
        
        return cur + amount;
    }

    @Override
    public boolean hasClaimedToday(int userId, String eventCode) {
        return pointEventMapper.countToday(userId, eventCode) > 0;
    }
}