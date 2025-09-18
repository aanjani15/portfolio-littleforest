package com.fintecho.littleforest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fintecho.littleforest.mapper.UserMapper;
import com.fintecho.littleforest.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	@Autowired
	UserService userService;
	@Autowired
	UserMapper userMapper;

	// 0홈 화면
	@GetMapping("/")
	public String home() {
		return "mainpage"; // templates/mainpage.html 렌더링
	}

	// 탄소 배출량 페이지
	@GetMapping("/emission1")
	public String emission() {
		return "emission1";
	}

	@GetMapping("/slider")
	public String sliderPage() {
		return "slider"; //
	}

	// 회사소개 페이지
	@GetMapping("/about")
	public String aboutPage() {
		return "about"; //
	}

}
