package com.fintecho.littleforest.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.admin.AdminStatsMapper;
import com.fintecho.littleforest.vo.admin.AdminCountsVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminStatsServiceImpl implements AdminStatsService {

	private final AdminStatsMapper mapper;

	// ===== 개별 =====
	@Override
	public AdminCountsVO getCounts() {
		return mapper.selectCounts();
	}

	@Override
	public Map<String, Object> getActive() {
		return mapper.selectActive();
	}

	@Override
	public List<Map<String, Object>> getMonthlyNewUsers() {
		return mapper.selectMonthlyNewUsers();
	}

	@Override
	public List<Map<String, Object>> getWeekdayAttendance() {
		return mapper.selectWeekdayAttendance();
	}

	@Override
	public List<Map<String, Object>> getTopStreaks() {
		return mapper.selectTopStreaks();
	}

	@Override
	public List<Map<String, Object>> getTreeLevelDistribution() {
		return mapper.selectTreeLevelDistribution();
	}

	@Override
	public List<Map<String, Object>> getGrownMonthly() {
		return mapper.selectGrownMonthly();
	}

	@Override
	public List<Map<String, Object>> getPointMonthlyByType() {
		return mapper.selectPointMonthlyByType();
	}

	@Override
	public List<Map<String, Object>> getGiftTopSent() {
		return mapper.selectGiftTopSent();
	}

	@Override
	public List<Map<String, Object>> getGiftTopReceived() {
		return mapper.selectGiftTopReceived();
	}

	@Override
	public List<Map<String, Object>> getSalesByCategory() {
		return mapper.selectSalesByCategory();
	}

	@Override
	public List<Map<String, Object>> getEmissionMonthly() {
		return mapper.selectEmissionMonthly();
	}

	@Override
	public Map<String, List<Map<String, Object>>> getCommunityMonthly() {
		Map<String, List<Map<String, Object>>> m = new HashMap<>();
		m.put("posts", mapper.selectCommunityPostsMonthly());
		m.put("comments", mapper.selectCommunityCommentsMonthly());
		m.put("likes", mapper.selectCommunityLikesMonthly());
		return m;
	}

	@Override
	public List<Map<String, Object>> getDonationMonthly() {
		return mapper.selectDonationMonthly();
	}

	@Override
	public AdminCountsVO getCountsAll() {
		AdminCountsVO base = mapper.selectCounts(); // 기본 카운트
		Map<String, Object> active = mapper.selectActive(); // 활성

		Map<String, List<Map<String, Object>>> community = getCommunityMonthly();

		int dau = ((Number) active.getOrDefault("DAU", active.getOrDefault("dau", 0))).intValue();
		int wau = ((Number) active.getOrDefault("WAU", active.getOrDefault("wau", 0))).intValue();
		int mau = ((Number) active.getOrDefault("MAU", active.getOrDefault("mau", 0))).intValue();

		base.setDau(dau);
		base.setWau(wau);
		base.setMau(mau);

		base.setMonthlyNewUsers(getMonthlyNewUsers());
		base.setWeekdayAttendance(getWeekdayAttendance());
		base.setTopStreaks(getTopStreaks());
		base.setTreeLevels(getTreeLevelDistribution());
		base.setGrownMonthly(getGrownMonthly());
		base.setPointMonthlyByType(getPointMonthlyByType());
		base.setGiftTopSent(getGiftTopSent());
		base.setGiftTopReceived(getGiftTopReceived());
		base.setSalesByCategory(getSalesByCategory());
		base.setEmissionMonthly(getEmissionMonthly());
		base.setCommunityPosts(community.get("posts"));
		base.setCommunityComments(community.get("comments"));
		base.setCommunityLikes(community.get("likes"));
		base.setDonationMonthly(getDonationMonthly());

		return base;
	}
}
