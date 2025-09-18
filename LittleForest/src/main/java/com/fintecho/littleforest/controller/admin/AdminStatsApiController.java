package com.fintecho.littleforest.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.fintecho.littleforest.service.admin.AdminStatsService;
import com.fintecho.littleforest.vo.admin.AdminCountsVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/stats")
@RequiredArgsConstructor
public class AdminStatsApiController {

	private final AdminStatsService service;

	@GetMapping("/counts")
	public AdminCountsVO counts() {
		return service.getCounts();
	}

	@GetMapping("/all")
	public AdminCountsVO all() {
		return service.getCountsAll();
	}

	@GetMapping("/active")
	public Map<String, Object> active() {
		return service.getActive();
	}

	@GetMapping("/users/monthly")
	public List<Map<String, Object>> usersMonthly() {
		return service.getMonthlyNewUsers();
	}

	@GetMapping("/attendance/weekday")
	public List<Map<String, Object>> attendanceWeekday() {
		return service.getWeekdayAttendance();
	}

	@GetMapping("/attendance/streaks")
	public List<Map<String, Object>> attendanceStreaks() {
		return service.getTopStreaks();
	}

	@GetMapping("/trees/levels")
	public List<Map<String, Object>> treeLevels() {
		return service.getTreeLevelDistribution();
	}

	@GetMapping("/trees/grown-monthly")
	public List<Map<String, Object>> grownMonthly() {
		return service.getGrownMonthly();
	}

	@GetMapping("/points/monthly-type")
	public List<Map<String, Object>> pointMonthlyByType() {
		return service.getPointMonthlyByType();
	}

	@GetMapping("/gifts/top-sent")
	public List<Map<String, Object>> giftTopSent() {
		return service.getGiftTopSent();
	}

	@GetMapping("/gifts/top-recv")
	public List<Map<String, Object>> giftTopRecv() {
		return service.getGiftTopReceived();
	}

	@GetMapping("/payments/category")
	public List<Map<String, Object>> salesByCategory() {
		return service.getSalesByCategory();
	}

	@GetMapping("/emission/monthly")
	public List<Map<String, Object>> emissionMonthly() {
		return service.getEmissionMonthly();
	}

	@GetMapping("/community/monthly")
	public Map<String, List<Map<String, Object>>> communityMonthly() {
		return service.getCommunityMonthly();
	}

	@GetMapping("/donation/monthly")
	public List<Map<String, Object>> donationMonthly() {
		return service.getDonationMonthly();
	}
	/*
	 * public AdminStatsApiController(AdminStatsService service) { this.service =
	 * service; }
	 * 
	 * @GetMapping("/admin/stats/counts") public AdminCountsVO getCounts() { return
	 * service.getCounts(); }
	 */
}
