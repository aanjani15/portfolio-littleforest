package com.fintecho.littleforest.service.admin;

import java.util.List;
import java.util.Map;

import com.fintecho.littleforest.vo.admin.AdminCountsVO;

public interface AdminStatsService {

	AdminCountsVO getCounts();

	Map<String, Object> getActive();

	List<Map<String, Object>> getMonthlyNewUsers();

	List<Map<String, Object>> getWeekdayAttendance();

	List<Map<String, Object>> getTopStreaks();

	List<Map<String, Object>> getTreeLevelDistribution();

	List<Map<String, Object>> getGrownMonthly();

	List<Map<String, Object>> getPointMonthlyByType();

	List<Map<String, Object>> getGiftTopSent();

	List<Map<String, Object>> getGiftTopReceived();

	List<Map<String, Object>> getSalesByCategory();

	List<Map<String, Object>> getEmissionMonthly();

	Map<String, List<Map<String, Object>>> getCommunityMonthly();

	List<Map<String, Object>> getDonationMonthly();

	// 한 방에(확장된 AdminCountsVO로 반환)
	AdminCountsVO getCountsAll();
}
