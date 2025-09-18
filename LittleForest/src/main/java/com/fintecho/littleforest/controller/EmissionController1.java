package com.fintecho.littleforest.controller;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.service.EmissionService;
import com.fintecho.littleforest.vo.EmissionMonthlyVO;
import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmissionController1 {

	private final EmissionService emissionService;

	// 페이지
	@GetMapping("/emission")
	public String emissionsPage(Integer year, Integer month, Model model, HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}
		int user_Id = loginUser.getId();

		YearMonth ym = (year == null || month == null) ? YearMonth.now() : YearMonth.of(year, month);
		
		model.addAttribute("user_id", user_Id);
		model.addAttribute("year", ym.getYear());
		model.addAttribute("month", ym.getMonthValue());
		return "emission"; // templates/emission.html
	}

	// API: 월별(1~12) 합계
	@ResponseBody
	@GetMapping("/api/emission/monthly")
	public List<EmissionMonthlyVO> apiMonthly(@RequestParam int user_Id, @RequestParam int year) {
		return emissionService.getMonthlyEmissions(user_Id, year);
	}

	// API: 선택 연/월 총합 (KPI)
	@ResponseBody
	@GetMapping("/api/emission/summary")
	public Map<String, Object> apiSummary(@RequestParam int user_Id, @RequestParam int year, @RequestParam int month) {
		int totalKg = emissionService.getMonthlyTotal(user_Id, year, month);
		return Map.of("totalKg", totalKg);
	}
}