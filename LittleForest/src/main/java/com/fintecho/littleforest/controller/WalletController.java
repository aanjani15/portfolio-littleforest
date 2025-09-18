package com.fintecho.littleforest.controller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fintecho.littleforest.mapper.WalletMapper;
import com.fintecho.littleforest.service.PaymentService;
import com.fintecho.littleforest.service.UserService;
import com.fintecho.littleforest.service.WalletService;
import com.fintecho.littleforest.vo.PaymentVO;
import com.fintecho.littleforest.vo.UserVO;
import com.fintecho.littleforest.vo.WalletVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WalletController {

	@Autowired
	WalletService walletService;
	
	@Autowired
	WalletMapper walletmapper;

	@Autowired
	UserService userService;

	@Autowired
	PaymentService paymentService;

	@RequestMapping("/wallet")
	public String walletlist(HttpSession session, Model model) {

		// int testUserId =1; //db 테스트용 유저 번호

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login";
		int userId = loginUser.getId();

		// 유저 정보 조회 후 model에 추가
		UserVO userInfo = userService.getInfo(userId);
		model.addAttribute("userInfo", userInfo);
		// 모달에 표시할 보유 포인트
		model.addAttribute("pointBalance", userInfo.getPoint());

		// user_id로 지갑 목록 전체 조회 (전체 계좌 정보)
		List<WalletVO> walletList = walletService.getWalletsByUserId(userId);
		model.addAttribute("walletList", walletList);

		// 총 잔액 계산
		int totalBalance = walletList.stream().mapToInt(WalletVO::getAccount_Balance).sum();
		model.addAttribute("totalBalance", totalBalance);

		// 통장 인덱스 walletList에서 중복제거하기 위한 Thymeleaf 사용중이라 따로 만들어야함
		// 중복 제거된 은행명 리스트 (탭생성용)
		Set<String> bankNames = walletList.stream().map(WalletVO::getBank_Name)
				.collect(Collectors.toCollection(LinkedHashSet::new));
		// 순서유지
		// Set <String > bankNames =[농협, 기업, 신한]
		model.addAttribute("bankNames", bankNames);
		model.addAttribute("showPassbook", false);

		return "walletlist";
	}
	
	@RequestMapping("/wallet/addBank")
	public ResponseEntity<Void> addBank(@RequestBody Map<String, Object> body, HttpSession session, Model model) {
		
		int id = (int) session.getAttribute("user_Id");
		
		String bankName = (String) body.get("bankName");
		String account_Number = (String) body.get("account_Number");
		
		System.out.println(id + " " + bankName + " " + account_Number);
		System.out.println(id + " " + bankName + " " + account_Number);
		System.out.println(id + " " + bankName + " " + account_Number);
		System.out.println(id + " " + bankName + " " + account_Number);
		
		walletmapper.insertWallet(id, bankName, account_Number);
		
		return ResponseEntity.noContent().build(); // 204
	}
	
	@RequestMapping("/wallet/addBalance")
	public ResponseEntity<Void> addBalance(@RequestBody Map<String, Object> body, HttpSession session, Model model) {
		
		int id = (int) session.getAttribute("user_Id");
		
		String bankName = (String) body.get("bankName");
		int amount = (int) body.get("amount");
		
		System.out.println(id + " " + bankName + " " + amount);
		System.out.println(id + " " + bankName + " " + amount);
		System.out.println(id + " " + bankName + " " + amount);
		System.out.println(id + " " + bankName + " " + amount);
		
		walletmapper.addBalance(id, bankName, amount);
		
		return ResponseEntity.noContent().build(); // 204
	}

	
	@RequestMapping("/wallet/desBalance")
	public ResponseEntity<Void> desBalance(@RequestBody Map<String, Object> body, HttpSession session, Model model) {
		
		int id = (int) session.getAttribute("user_Id");
		
		int wallet_Id = (int) body.get("wallet_Id");
		int amount = (int) body.get("amount");
		
		
		walletmapper.desBalance(id, wallet_Id, amount);
		
		return ResponseEntity.noContent().build(); // 204
	}
	
	@RequestMapping("/payment")
	public String paymentlist(HttpSession session, Model model) {
		// int testUserId = 5;

		/* =====================추가==================== */
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null)
			return "redirect:/login";
		int userId = loginUser.getId();

		// 유저 정보
		UserVO userInfo = userService.getInfo(userId);
		model.addAttribute("userInfo", userInfo);

		// 지갑 내역
		List<PaymentVO> paymentList = paymentService.getPaymentListByUserId(userId);
		// log.info("결제 내역 수: {}", paymentList.size()); db에 값 없을때 대비
		model.addAttribute("paymentList", paymentList);

		// ⬇ 총잔액 계산 추가 (walletService 이용)
		List<WalletVO> walletList = walletService.getWalletsByUserId(userId);
		int totalBalance = walletList.stream().mapToInt(WalletVO::getAccount_Balance).sum();
		model.addAttribute("totalBalance", totalBalance);

		return "payment";
	}

}
