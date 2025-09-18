package com.fintecho.littleforest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintecho.littleforest.dto.NetZeroPointRequest;
import com.fintecho.littleforest.dto.NetZeroPointResponse;
import com.fintecho.littleforest.service.CodefService;
import com.fintecho.littleforest.vo.UserVO;

import io.codef.api.EasyCodefUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 한 페이지에서: 입력(Form) → 조회(POST) → 출력(View) - 내부 도메인(user_id) => 외부(userId) 변환은
 * NetZeroPointRequest#toCodefBody에서 수행
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/carbon")
public class CarbonController {

	private final CodefService codefService;
	// private final ObjectMapper objectMapper = new ObjectMapper();
	// private final ObjectMapper objectMapper; // 스프링이 빈 주입

	@Value("${codef.public-key}")
	private String publicKey;

	/** 조회 폼 + 결과 영역 (GET) */
	@GetMapping({ "/point", "/carbonpoint" })
	public String page(Model model) {
		if (!model.containsAttribute("form"))
			model.addAttribute("form", new NetZeroPointRequest());
		return "carbonpoint";
	}

	@PostMapping("/netzero")
	public String netzero(@ModelAttribute("form") NetZeroPointRequest form, Model model, HttpSession session) {

		try {
			UserVO loginUser = (UserVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				return "redirect:/login";
			}

			// 1) RSA 암호화 포함한 요청 바디 구성
			Map<String, Object> body = new HashMap<>();
			body.put("organization", "0002");
			body.put("userId", form.getUserId());
			body.put("password", EasyCodefUtil.encryptRSA(form.getPassword(), publicKey));
			if (form.getYear() != null && !form.getYear().isBlank())
				body.put("year", form.getYear());

			// 2) CODEF 호출
			String json = codefService.getNetZeroPoint(body);
			model.addAttribute("resultJson", json);

			// 3) 성공/실패 판별 후 화면 바인딩
			ObjectMapper om = new ObjectMapper();
			JsonNode root = om.readTree(json);
			String code = root.path("result").path("code").asText();
			if (!"CF-00000".equals(code)) {
				String msg = root.path("result").path("message").asText();
				String extra = root.path("result").path("extraMessage").asText();
				model.addAttribute("success", false);
				model.addAttribute("errorMessage", code + " " + msg + " " + extra);
				return "carbonpoint";
			}

			// data → DTO 매핑(화면의 요약/표에 바인딩)
			NetZeroPointResponse result = om.treeToValue(root.path("data"), NetZeroPointResponse.class);

			model.addAttribute("success", true);
			model.addAttribute("result", result);
			return "carbonpoint";

		} catch (Exception e) {
			log.error("netzero error", e);
			model.addAttribute("success", false);
			model.addAttribute("errorMessage", e.getMessage());
			return "carbonpoint";
		}
	}
}