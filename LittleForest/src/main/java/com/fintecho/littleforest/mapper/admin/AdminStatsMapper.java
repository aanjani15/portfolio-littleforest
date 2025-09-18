package com.fintecho.littleforest.mapper.admin;

import java.util.List;
import java.util.Map;

import com.fintecho.littleforest.vo.admin.AdminCountsVO;

public interface AdminStatsMapper {
	AdminCountsVO selectCounts();

	Map<String, Object> selectActive();

	List<Map<String, Object>> selectMonthlyNewUsers();

	List<Map<String, Object>> selectWeekdayAttendance();

	List<Map<String, Object>> selectTopStreaks();

	List<Map<String, Object>> selectTreeLevelDistribution();

	List<Map<String, Object>> selectGrownMonthly();

	List<Map<String, Object>> selectPointMonthlyByType();

	List<Map<String, Object>> selectGiftTopSent();

	List<Map<String, Object>> selectGiftTopReceived();

	List<Map<String, Object>> selectSalesByCategory();

	List<Map<String, Object>> selectEmissionMonthly();

	List<Map<String, Object>> selectCommunityPostsMonthly();

	List<Map<String, Object>> selectCommunityCommentsMonthly();

	List<Map<String, Object>> selectCommunityLikesMonthly();

	List<Map<String, Object>> selectDonationMonthly();
}
