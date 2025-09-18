package com.fintecho.littleforest.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.service.AttendanceService;
import com.fintecho.littleforest.service.GrowingTreeService;
import com.fintecho.littleforest.service.PointService;
import com.fintecho.littleforest.service.UserService;
import com.fintecho.littleforest.vo.AttendanceVO;
import com.fintecho.littleforest.vo.GrowingTreeVO;
import com.fintecho.littleforest.vo.PointVO;
import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class TreeController {

	@Autowired
	private GrowingTreeService growingTreeService;

	@Autowired
	private UserService userService;

	@Autowired
	private PointService pointService;

	@Autowired
	private AttendanceService attendanceService;

	@GetMapping("/growtree")
	public String tree(Model model, HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}

		int user_Id = loginUser.getId();

		UserVO vo2 = userService.getinform(user_Id);
		model.addAttribute("users", vo2);

		GrowingTreeVO vo = growingTreeService.getAllStock(user_Id);
		// int user_seq=(int) session.getAttribute("user_seq");
		boolean iftree = growingTreeService.ifTree(user_Id);

		if (!iftree) {
			model.addAttribute("iftree", false);
			return "tree";
		} else {
			vo = growingTreeService.getAllStock(user_Id);
			int level = vo.getTree_Level();
			String name = vo.getTree_Name();
			System.out.println(name);
			String tree_image = null;
			String status = null;
			switch (level) {
			case 1:
				tree_image = "/image/seed.png";
				status = "1레벨";
				break;
			case 2:
				tree_image = "/image/saessak.png";
				status = "2레벨";
				break;
			case 3:
				tree_image = "/image/babytree.png";
				status = "3레벨";
				break;
			case 4:
				tree_image = "/image/middletree.png";
				status = "4레벨";
				break;
			case 5:
				tree_image = "/image/middletree2.png";
				status = "5레벨";
				break;
			case 6:
				tree_image = "/image/middletree3.png";
				status = "6레벨";
				break;
			case 7:
				tree_image = "/image/wansungtree.png";
				status = "7레벨";
				break;
			}
			// UserVO vo2 = userService.getinform(user_Id);

			boolean event = randomEvent();
			if (event) {
				System.out.println("이벤트발생");
			} else {
				System.out.println("이벤트발생안함");
			}
			model.addAttribute("status", status);
			model.addAttribute("stock", vo);

			// model.addAttribute("users", vo2);

			model.addAttribute("tree_Image", tree_image);
			model.addAttribute("iftree", true);
		}
		return "tree";
	}

	@PostMapping("/growtree/plant")
	public ResponseEntity<String> plant(Model model, HttpSession session, @RequestBody InsertRequest request) {
		// int user_Id = 5;
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.status(401).body("로그인이 필요합니다.");
		}
		int user_Id = loginUser.getId();

		GrowingTreeVO treevo = new GrowingTreeVO();
		treevo.setTree_Name(request.tree_Name);
		treevo.setUser_Id(user_Id);
		System.out.println("tree_Name: " + request.tree_Name);

		int result = growingTreeService.insertTree(treevo);

		return (result > 0) ? ResponseEntity.ok("success") : ResponseEntity.status(500).body("fail");
	}

	@PostMapping("/growtree/happen")
	public ResponseEntity<String> happening(HttpSession session) {
		// int user_Id = 5;
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.status(401).body("로그인이 필요합니다.");
		}
		int user_Id = loginUser.getId();

		UserVO user = userService.getinform(user_Id);
		user.setId(user_Id);
		int point = user.getPoint();
		if (point < 100) {
			return ResponseEntity.status(500).body("fail");
		} else {
			PointVO pvo = new PointVO();
			pvo.setUser_Id(user_Id);
			pointService.randomcost(pvo);
		}

		return ResponseEntity.ok("success");

	}

	/** 이번 달 출석일 목록 */
	@GetMapping("/attendance/dates")
	@ResponseBody
	public ResponseEntity<List<String>> getAttendanceDates(@RequestParam("user_Id") int user_id) {
		List<String> list = attendanceService.getAttendanceDates(user_id);
		System.out.println("요청 들어온 user_Id = " + user_id);

		return ResponseEntity.ok().header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0")
				.header("Pragma", "no-cache").header("Expires", "0").body(list);
	}

	/** 오늘 출석 여부 */
	@GetMapping("/attendance/today")
	@ResponseBody
	public boolean hasTodayAttendance(@RequestParam("user_Id") int userId) {
		AttendanceVO vo = new AttendanceVO();
		vo.setUser_Id(userId);
		return attendanceService.hasAttendedToday(vo);
	}

	/** 출석 체크 + 보상 지급 */
	@PostMapping("/attendance/check")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> checkAttendance(@RequestBody AttendanceVO vo) {
		Map<String, Object> result = new HashMap<>();

		if (attendanceService.hasAttendedToday(vo)) {
			result.put("message", "이미 출석했습니다.");
			result.put("success", false);
			return ResponseEntity.ok(result);
		}

		attendanceService.insertAttendance(vo);
		int total = attendanceService.getTotalAttendanceCount(vo.getUser_Id());

		LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
		boolean isWeekend = today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY;
		int waterReward = isWeekend ? 2 : 1;
		boolean fertilizerReward = (total == 10 || total == 20 || total == 30);
		int biyroReward = fertilizerReward ? 1 : 0;

		attendanceService.addAttendanceRewardToInventory(vo.getUser_Id(), waterReward, biyroReward);
		Map<String, Object> stocks = attendanceService.getCurrentStocks(vo.getUser_Id());

		result.put("message", "출석 완료!");
		result.put("success", true);
		result.put("water", waterReward);
		result.put("fertilizer", fertilizerReward);
		result.put("totalAttendance", total);
		result.put("waterStock", stocks.get("WATER_STOCK"));
		result.put("biyroStock", stocks.get("BIYRO_STOCK"));

		return ResponseEntity.ok(result);
	}

	public static class InsertRequest {
		private String tree_Name;

		public String getTree_Name() {
			return tree_Name;
		}

		public void setTree_Name(String tree_Name) {
			this.tree_Name = tree_Name;
		}

	}

	public boolean randomEvent() {
		Random r1 = new Random();
		return r1.nextDouble() < 0.1;

	}
}
