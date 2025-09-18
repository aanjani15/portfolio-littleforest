package com.fintecho.littleforest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fintecho.littleforest.service.UserService;
import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

	@Autowired
	UserService userService;

	private final PasswordEncoder passwordEncoder;
	/*
	 * @GetMapping("/") public String home() { return "mainpage"; //
	 * templates/index.html 로 연결됨 (확장자 제외) }
	 */

	/* @RequestMapping("/mypage") */
	@GetMapping("/mypage")
	public String mypage(HttpSession session, Model model) {

		// int User_Id = 1;
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login"; // 로그인 안 했으면 로그인 페이지로
		}

		int user_Id = loginUser.getId();
		UserVO userInfo = userService.getInfo(user_Id);

		// (프로필 사진) 가공된 경로 직접 설정
		String profilePath = (userInfo.getProfile_Photo() == null || userInfo.getProfile_Photo().isBlank())
				? "profile.png"
				: userInfo.getProfile_Photo();

///////////////////// 09.03 추가 세션에 저장 (사이드바가 여기서 꺼내 씀) //////////////////////
		session.setAttribute("userInfo", userInfo);
		session.setAttribute("profilePhotoPath", profilePath);
///////////////////// 09.03 추가 세션에 저장 (사이드바가 여기서 꺼내 씀) //////////////////////

		model.addAttribute("profilePhotoPath", profilePath); // 별도로 전달
		// Thymeleaf로 전달
		model.addAttribute("userInfo", userInfo);

		return "mypage";
	}

	@PostMapping("/mypage/update/profile")

	public ResponseEntity<?> profileU(@RequestParam("id") int id,
			@RequestParam(value = "profileUrl", required = false) String profileUrl) {
		UserVO uvo = userService.getInfo(id);
		if (!profileUrl.isEmpty() && profileUrl != null) {
			uvo.setProfile_Photo(profileUrl);
		} else {
			uvo.setProfile_Photo(null);
		}
		userService.userUpdateProfile(uvo);

		return ResponseEntity.ok("success");

	}

	@PostMapping("/mypage/update/nicknamecheck")
	public ResponseEntity<String> nicknameCheck(@RequestParam("nickname") String nickname) {
		int count = 0;
		try {
			count = userService.nicknameChecking(nickname);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("error");
		}

		if (count == 0) {
			System.out.println("성공");
			return ResponseEntity.ok("success");
		} else {
			System.out.println("중복");
			return ResponseEntity.ok("fail");
		}
	}

	@PostMapping("/mypage/update/info")
	public ResponseEntity<String> infoUpdate(@RequestParam("id") int id, @RequestParam("nickname") String nickname,
			@RequestParam("address") String address, @RequestParam("phone") String phone,
			@RequestParam("email") String email) {
		UserVO uvo = userService.getInfo(id);
		uvo.setAddress(address);
		uvo.setEmail(email);
		uvo.setNickname(nickname);
		uvo.setPhone(phone);
		uvo.setProfile_Photo(uvo.getProfile_Photo());
		userService.userUpdate(uvo);
		return ResponseEntity.ok("success");

	}

	@PostMapping("/mypage/update/password")
	public ResponseEntity<?> checkPassword(@RequestParam("id") int id, @RequestParam("password") String password) {
		UserVO uvo = userService.getInfo(id);
		String dbPassword = uvo.getPassword();
		if (passwordEncoder.matches(password, dbPassword)) {
			// if(dbPassword.equals(password)) { /* 암호화가 안된 비밀번호라면 주석을 해제하고 쓸것 */
			System.out.println("일치");
			return ResponseEntity.ok("success");

		} else
			System.out.println("불일치");
		return ResponseEntity.badRequest().body("fail");

	}

	@PostMapping("/mypage/update/password/new")
	public ResponseEntity<?> revisePassword(@RequestParam("id") int id, @RequestParam("password") String password) {
		UserVO uvo = userService.getInfo(id);
		uvo.setPassword(passwordEncoder.encode(password));
		userService.userUpdatePassword(uvo);
		return ResponseEntity.ok("success");
	}

}
