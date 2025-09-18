package com.fintecho.littleforest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.mapper.UserMapper;
import com.fintecho.littleforest.service.UserService;
import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

	@Autowired
	UserService userService;
	@Autowired
	UserMapper userMapper;

	private final PasswordEncoder passwordEncoder;

	// 로그인 화면
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	// 로그인 !
	@PostMapping("/login")
	public String login(@RequestParam String oauth_id, @RequestParam String password, Model model,
			HttpSession session) {

		UserVO user = userService.findByOauthID(oauth_id);
		

		//if (user != null && user.getPassword().equals(password)) {
			if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			session.setAttribute("loginUser", user);
			session.setAttribute("userInfo", user);
			session.setAttribute("user_Id", user.getId());
			session.setAttribute("user_No", user.getId());
			
			session.setAttribute("profilePhotoPath", user.getProfile_Photo());

			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "login";
		}
	}

	// 회원가입 처리
	
	@PostMapping("/join")
	public String joinSubmit(@RequestParam String name, @RequestParam String oauth_id, @RequestParam String birth,
			@RequestParam String phone, @RequestParam String password, @RequestParam String address,
			@RequestParam String detail_address, @RequestParam String email) {
		UserVO user = new UserVO();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birthDate = sdf.parse(birth);
			user.setBirth(birthDate);
		} catch (ParseException e) {
			e.printStackTrace();
			// 예외 처리 (예: 기본값 지정, 오류 메시지 전달 등)
		}

		user.setName(name);
		user.setOauth_Id(oauth_id);
		user.setNickname(oauth_id);
		user.setPhone(phone);
		user.setPassword(passwordEncoder.encode(password));
		user.setAddress(address + " " + detail_address);
		user.setEmail(email);

		userService.joinUser(user);
		
		return "redirect:/login";
	}

	@PostMapping("/kakaojoin")
	public String kakaojoinSubmit(@RequestParam String name, @RequestParam String oauth_id, @RequestParam String birth,
			@RequestParam String phone, @RequestParam String address,
			@RequestParam String detail_address, @RequestParam String email) {
		
		UserVO user = new UserVO();
		
		try {
			SimpleDateFormat BirthParsing = new SimpleDateFormat("yyyy-MM-dd");
			Date birthDate = BirthParsing.parse(birth);
			user.setBirth(birthDate);
		} catch (ParseException e) {
			e.printStackTrace();
			// 예외 처리 (예: 기본값 지정, 오류 메시지 전달 등)
		}
		
		user.setOauth_Id(oauth_id);
		user.setName(name);
		user.setNickname(name);
		user.setPhone(phone);
		user.setPassword("kakao_account");
		user.setAddress(address + " " + detail_address);
		user.setEmail(email);
		
		userService.joinUser(user);
		return "redirect:/login";
	}
	
	@GetMapping("/join")
	public String join(HttpSession session, Model model) {

		return "join"; // join.html
	}

	@PostMapping("/checkemailandid")
	@ResponseBody
	public int checkemail(@RequestBody Map<String,String> body, Model model) {

		if (userMapper.checkEmailAndIdDupl(body.get("id"), body.get("email")) > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@PostMapping("/joinkakao/session")
	public ResponseEntity<Void> joinkakaoSession(@RequestBody Map<String,String> body, HttpSession session) {
		
		System.out.println(body.get("id"));
		System.out.println(body.get("email"));
		session.setAttribute("kakao_Id", body.get("id"));
		session.setAttribute("kakao_Email", body.get("email"));
		
		return ResponseEntity.noContent().build(); // 204
	}
	
	@GetMapping("/joinkakao")
	public String joinkakao( HttpSession session) {
		
		return "kakaojoin"; 
	}

	@PostMapping("/loginkakao")
	public ResponseEntity<Void> loginkakao(@RequestBody Map<String, String> body, HttpSession session) {

		String id = body.get("id");

		UserVO user = userService.findByOauthID(id);
		
		
		System.out.println(user.getId());
		System.out.println(user.getId());
		System.out.println(user.getId());
		System.out.println(user.getId());
		System.out.println(user.getId());
		
		session.setAttribute("loginUser", user);
		session.setAttribute("user_Id", user.getId());
		session.setAttribute("user_No", user.getId());

		return ResponseEntity.noContent().build(); // 204
	}

}
