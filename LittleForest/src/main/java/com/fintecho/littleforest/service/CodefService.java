package com.fintecho.littleforest.service;

import java.util.Map;

public interface CodefService {
	String getNetZeroPoint(Map<String, Object> body) throws Exception;
    String getAccessTokenForDemo() throws Exception; // (선택) 필요시 토큰 직접 확인용
}




/*
 * CODEF 서비스 래퍼 (토큰/요청/호출 담당) 컨트롤러에서 직접 SDK를 만지지 않고, 서비스 계층에서 일괄 처리
 * SDK가 토큰 캐시/자동 재발급을 처리 → 컨트롤러는 비즈니스 로직에 집중
 */