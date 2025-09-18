package com.fintecho.littleforest.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fintecho.littleforest.service.AnswerService;
import com.fintecho.littleforest.service.InqueryService;
import com.fintecho.littleforest.service.UserService;
import com.fintecho.littleforest.vo.AnswerVO;
import com.fintecho.littleforest.vo.InqueryVO;
import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class InqueryController {

	@Autowired
	private InqueryService inqueryService;
	@Autowired
	private AnswerService answerService;

	@Autowired
	private UserService userService;

	@GetMapping("/contact")
	public String inquery(Model model, HttpSession session) {
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}

		int user_Id = loginUser.getId();

		UserVO uvo = userService.getinform(user_Id);
		ArrayList<InqueryVO> ivo = inqueryService.getUserInquery(user_Id);

		ArrayList<AnswerVO> allAnswers = new ArrayList<>();
		for (InqueryVO inquery : ivo) {
			ArrayList<AnswerVO> answersForThis = answerService.getAnswer(inquery.getId());
			allAnswers.addAll(answersForThis);
		}

		// Model에 담기
		model.addAttribute("user", uvo);
		model.addAttribute("inquery", ivo);
		model.addAttribute("answer", allAnswers); // 전체 답변 리스트

		return "inquery";
	}

	@PostMapping("/contact/delete")
	public ResponseEntity<String> inDelete(@RequestParam("id") int id, @RequestParam("user_Id") int user_Id,
			HttpSession session) {
		try {
			InqueryVO ivo = new InqueryVO();
			ivo.setId(id);
			ivo.setUser_Id(user_Id);
			// Integer user_Id2=(Integer) session.getAttribute("id");
			// if(user_Id2==null){
			// return ResponseEntity.internalServerError().body("fail");}
			inqueryService.deleteInquery(ivo);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("error : " + e.getMessage());
		}
		return ResponseEntity.ok("success");
	}

	@PostMapping("/contact/one")
	public ResponseEntity<InqueryVO> ing(@RequestParam("id") int id, @RequestParam("user_Id") int user_Id) {
		InqueryVO ivo = inqueryService.getOneInquery(id, user_Id);
		ivo.setId(id);
		ivo.setUser_Id(user_Id);
		return ResponseEntity.ok(ivo);

	}

	@PostMapping("/contact/update")
	public ResponseEntity<?> inup(@RequestParam("id") int id, @RequestParam("user_Id") int user_Id,
			@RequestParam("category") String category, @RequestParam("title") String title,
			@RequestParam("content") String content) {
		try {
			InqueryVO ivo = inqueryService.getOneInquery(id, user_Id);
			ivo.setCategory(category);
			ivo.setTitle(title);
			ivo.setContent(content);
			inqueryService.updateInquery(ivo);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("error :" + e.getMessage());
		}
		return ResponseEntity.ok("success");

	}

	@PostMapping("/contact/insert")
	public ResponseEntity<?> inin(@RequestParam("category") String category, @RequestParam("title") String title,
			@RequestParam("content") String content, HttpSession session) {

		try {
			UserVO loginUser = (UserVO) session.getAttribute("loginUser");
			if (loginUser == null) {
				return ResponseEntity.status(401).body("로그인이 필요합니다");
			}
			int user_Id = loginUser.getId();
			InqueryVO ivo = new InqueryVO();
			ivo.setUser_Id(user_Id);
			ivo.setCategory(category);
			ivo.setContent(content);
			ivo.setTitle(title);
			inqueryService.insertInquery(ivo);
		} catch (Exception e) {
			e.printStackTrace(); // 콘솔에 실제 원인 출력
			return ResponseEntity.internalServerError().body("error");
		}
		return ResponseEntity.ok("success");

	}

	@PostMapping("/contact/answer/insert")
	public ResponseEntity<String> answerin(@RequestParam("inquery_Id") int inquery_Id,
			@RequestParam("answer_Id") int answer_Id, @RequestParam("content") String content, HttpSession session) {
		try {
			Integer user_Id = (Integer) session.getAttribute("id");
			if (user_Id == null) {
				user_Id = 1;
			}
			AnswerVO avo = new AnswerVO();
			avo.setUser_Id(user_Id);
			avo.setInquery_Id(inquery_Id);
			avo.setAnswer_Id(answer_Id);
			avo.setContent(content);
			answerService.insertAnswer(avo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("error");
		}
		return ResponseEntity.ok("success");

	}
}
