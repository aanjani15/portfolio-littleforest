package com.fintecho.littleforest.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fintecho.littleforest.service.admin.AdminMemberService;
import com.fintecho.littleforest.vo.UserVO;
import com.fintecho.littleforest.vo.admin.AdminVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {
	private final AdminMemberService svc;

	@GetMapping
	public String list(@ModelAttribute AdminVO q, Model model) {
		model.addAttribute("res", svc.list(q));
		model.addAttribute("q", q);
		model.addAttribute("title", "회원관리");
		return "admin/members";
	}

	@PostMapping("/role")
	@ResponseBody
	public ResponseEntity<?> role(@RequestParam int id, @RequestParam String role) {
		svc.changeRole(id, role);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable int id, Model model) {
		model.addAttribute("u", svc.findById(id));
		model.addAttribute("title", "회원 상세");
		return "admin/member-detail";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable int id, @ModelAttribute UserVO form, RedirectAttributes ra) {
		form.setId(id);
		svc.updateBasic(form);
		ra.addFlashAttribute("msg", "저장되었습니다.");
		return "redirect:/admin/members/{id}";
	}

}
