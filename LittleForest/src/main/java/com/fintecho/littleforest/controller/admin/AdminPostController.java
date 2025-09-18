package com.fintecho.littleforest.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.service.admin.AdminPostService;
import com.fintecho.littleforest.vo.CommentVO;
import com.fintecho.littleforest.vo.CommunityVO;
import com.fintecho.littleforest.vo.admin.AdminVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/posts")
@RequiredArgsConstructor
public class AdminPostController {
	private final AdminPostService svc;

	@GetMapping
	public String list(@ModelAttribute AdminVO q, Model model) {
		model.addAttribute("res", svc.list(q));
		model.addAttribute("q", q);
		model.addAttribute("title", "게시판관리");
		return "admin/posts";
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable int id) {
		try {
			svc.deletePost(id);
			return ResponseEntity.ok().body("삭제 성공");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("삭제 실패: " + e.getMessage());
		}
	}

	
	@DeleteMapping("/comments/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable int id) {
		try {
			svc.deleteComment(id);
			return ResponseEntity.ok().body("삭제 성공");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("삭제 실패: " + e.getMessage());
		}
	}

	@PostMapping("/cleanup")
	@ResponseBody
	public ResponseEntity<?> cleanup(@RequestParam List<String> allowed) {
		if (allowed == null || allowed.isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "허용 타입이 비어있음"));
		}
		int removed = svc.bulkDeleteWeirdTypes(allowed);
		return ResponseEntity.ok(Map.of("removed", removed));
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable int id, Model model) {
		CommunityVO p = svc.findPost(id);
		List<CommentVO> comments = svc.findComments(id);
		System.out.println("comments.size = " + (comments == null ? "null" : comments.size()));
		model.addAttribute("p", p);
		model.addAttribute("comments", comments);
		model.addAttribute("title", "게시글 상세");
		return "admin/post-detail";
	}

}
