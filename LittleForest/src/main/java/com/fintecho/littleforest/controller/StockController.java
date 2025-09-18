package com.fintecho.littleforest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fintecho.littleforest.service.GrowingTreeService;
import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@RestController
public class StockController {

	@Autowired
	private GrowingTreeService growingTreeService;

	// 컨트롤러에서 dto 선언
	public static class GiveRequest {
		private int havewater;
		private int water_Stock;

		public int getHavewater() {
			return havewater;
		}

		public void setHavewater(int havewater) {
			this.havewater = havewater;
		}

		public int getWater_Stock() {
			return water_Stock;
		}

		public void setWater_Stock(int water_Stock) {
			this.water_Stock = water_Stock;
		}
	}

	public static class BiyroRequest {
		private int biyro_Stock;

		public int getBiyro_Stock() {
			return biyro_Stock;
		}

		public void setBiyro_Stock(int biyro_Stock) {
			this.biyro_Stock = biyro_Stock;
		}

	}

	@PostMapping("/growtree/give")

	public ResponseEntity<String> give(@RequestBody GiveRequest request, HttpSession session) {
		try {
			// int user_Id = 4; //임시
			UserVO loginUser = (UserVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				return ResponseEntity.status(401).body("로그인이 필요합니다.");
			}
			int user_Id = loginUser.getId();

			growingTreeService.updatews(user_Id);

			return ResponseEntity.ok("성공");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("error: " + e.getMessage());
		}
	}

	@PostMapping("/growtree/biyro")

	public ResponseEntity<String> biyro(@RequestBody BiyroRequest request, HttpSession session) {
		try {
			// int user_Id = 4;
			UserVO loginUser = (UserVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				return ResponseEntity.status(401).body("로그인이 필요합니다.");
			}
			int user_Id = loginUser.getId();

			growingTreeService.updatebiyro(request.getBiyro_Stock(), user_Id);
			return ResponseEntity.ok("성공");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("error: " + e.getMessage());
		}

	}

}
