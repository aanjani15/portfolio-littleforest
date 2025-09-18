package com.fintecho.littleforest.service;

public interface EventService {

	class AlreadyClaimedTodayException extends RuntimeException {
	}

	/** 하루 1회 보장 적립 (eventCode별 1일 1회) */
	int claimDaily(int userId, String eventCode, int amount, String memo) throws AlreadyClaimedTodayException;

	/** 오늘 이미 받았는지 여부(선택: UI 상태 표시용) */
	boolean hasClaimedToday(int userId, String eventCode);
}