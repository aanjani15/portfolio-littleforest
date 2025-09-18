package com.fintecho.littleforest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fintecho.littleforest.service.GrowingTreeService;
import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@RestController
public class PurchaseController {

	@Autowired
	private GrowingTreeService growingTreeService;

	@PostMapping("/growtree/purchase")
	public ResponseEntity<String> purchase(@RequestBody PurchaseRequest request, HttpSession session) {
		// int user_Id = 4; // 나중에 실제 로그인한 사용자 ID로 변경
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		int user_Id = loginUser.getId();

		try {
			// 실제 구매 서비스 호출 (트랜잭션 처리)
			growingTreeService.updatestock(request.getQuantity(), user_Id, request.getTotalprice());

			return ResponseEntity.ok("성공");
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("구매 중 오류 발생");

		}

	}

	// DTO 클래스
	public static class PurchaseRequest {
		private int quantity;
		private int totalprice;

		// getter, setter
		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public int getTotalprice() {
			return totalprice;
		}

		public void setTotalprice(int totalprice) {
			this.totalprice = totalprice;
		}
	}
}