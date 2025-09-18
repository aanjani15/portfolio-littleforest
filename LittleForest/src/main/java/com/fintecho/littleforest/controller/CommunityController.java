package com.fintecho.littleforest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.service.CommentService;
import com.fintecho.littleforest.service.CommunityService;
import com.fintecho.littleforest.service.LikesService;
import com.fintecho.littleforest.service.UserService;
import com.fintecho.littleforest.vo.CommentVO;
import com.fintecho.littleforest.vo.CommunityVO;
import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommunityController {
	@Autowired
	private CommunityService communityService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private LikesService likesService;

	@GetMapping("/community")
	public String community(@RequestParam(value = "sort", required = false, defaultValue = "desc") String sort,
			@RequestParam(value = "type", required = false) List<String> type,
			@RequestParam(value = "my", required = false) boolean my, Model model, HttpSession session) {

		// 로그인 유저 임시
		// int user_Id = 2;
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}
		int user_Id = loginUser.getId();

		UserVO uservo = userService.getinform(user_Id);
		ArrayList<Integer> check = likesService.checklikes(user_Id);
		// 파라미터 확인
		System.out.println("sort = " + sort);
		System.out.println("type = " + type);
		System.out.println("my== " + my);

		// Map으로 서비스에 전달
		Map<String, Object> params = new HashMap<>();
		params.put("sort", sort);
		params.put("type", type);
		if (my) {
			params.put("user_Id", user_Id); // my 체크했을 때만 userId 조건 전달
		}

		List<CommunityVO> vo = communityService.selectTypeCommunity(params);

		model.addAttribute("check", check);
		model.addAttribute("user", uservo);
		model.addAttribute("sort", sort);
		model.addAttribute("type", type);
		model.addAttribute("my", user_Id);
		model.addAttribute("com", vo);

		return "community";
	}

	// 커뮤니티 글 하나 상세보기
	@GetMapping("/community/status")
	public ResponseEntity<CommunityVO> one(@RequestParam("id") int id, Model model) {
		CommunityVO vo = communityService.selectOneCommunity(id);
		System.out.println(id);
		if (vo != null) {
			return ResponseEntity.ok(vo);
		} else {
			return ResponseEntity.badRequest().body(vo);
		}

	}

	// 커뮤니티 해당글의 댓글 개수보기 기능
	@GetMapping("/community/comment")
	public ResponseEntity<ArrayList<CommentVO>> two(@RequestParam("community_Id") int community_Id) {
		ArrayList<CommentVO> vo = commentService.selectComment(community_Id);
		return ResponseEntity.ok(vo);
	}

	// 커뮤니티 해당글 자세히보기 기능(로그인시 세션에서 유저 아이디 가져와야함
	@GetMapping("/community/morecomment")
	public String haha(@RequestParam("community_Id") int community_Id, Model model, HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}
		int user_Id = loginUser.getId();

		ArrayList<CommentVO> vo = commentService.selectComment(community_Id);
		CommunityVO voo = communityService.selectOneCommunity(community_Id);
		// int user_Id = 2;// 임시
		UserVO o = userService.getinform(user_Id);// 임시
		ArrayList<Integer> check = likesService.checklikes(user_Id);
		// int id=(int) session.getAttribute("userid");

		model.addAttribute("o", o);
		model.addAttribute("voo", voo);
		model.addAttribute("vo", vo);
		model.addAttribute("k", check);
		return "morecommunity";

	}

	// 커뮤니티 등록
	@PostMapping("/community/insert")
	public ResponseEntity<?> inserting(@RequestParam("type") String type, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam(value = "photo", required = false) String photoUrl,
			HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}
		int user_Id = loginUser.getId();

		try {
			CommunityVO vo = new CommunityVO();
			vo.setUser_Id(user_Id);
			vo.setType(type);
			vo.setTitle(title);
			vo.setContent(content);
			vo.setPhoto(photoUrl); // JS에서 Cloudinary URL 전달

			communityService.insertCommunity(vo);

			return ResponseEntity.ok("success");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
		}
	}

	// 내 커뮤니티 글만 보기
	@GetMapping("/community/my")
	public String mycommunity(Model model, HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}
		int user_Id = loginUser.getId();

		ArrayList<CommunityVO> vo = communityService.selectUserCommunity(user_Id);
		model.addAttribute("my", vo);
		return "myCommunity";

	}

	// 커뮤니티 수정
	@PostMapping("/community/update")
	public ResponseEntity<?> updatecommunity(@RequestParam("id") int id, @RequestParam("type") String type,
			@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam(value = "photoPath", required = false) String photourl, HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		int user_Id = loginUser.getId();

		CommunityVO vo = communityService.selectOwnCommunity(id, user_Id);
		vo.setType(type);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setLikes(vo.getLikes());

		if (photourl != null && !photourl.isEmpty()) {
			vo.setPhoto(photourl);
		} else if (vo.getPhoto() != null) {
			vo.setPhoto(vo.getPhoto());
		} else {
			vo.setPhoto(null);
		}

		communityService.updateCommunity(vo);

		return ResponseEntity.ok("success");
	}

	// 커뮤니티 삭제
	@PostMapping("/community/delete")
	public ResponseEntity<?> del(@RequestParam("id") int id, HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		int user_Id = loginUser.getId();

		CommunityVO vo = new CommunityVO();
		vo.setId(id);
		vo.setUser_Id(user_Id);
		vo.setTitle(communityService.selectOneCommunity(id).getTitle());

		UserVO user = userService.getinform(user_Id);
		if (user.getRole().equals("admin")) {
			communityService.deleteAdmin(vo);
		} else {
			communityService.deleteCommunity(vo);
		}
		return ResponseEntity.ok("success");

	}

	// 커뮤니티 댓글 등록
	@PostMapping("/community/comment/insert")
	public ResponseEntity<?> how(@RequestParam("community_Id") int community_Id,
			@RequestParam("comment_Id") String comment_Id, HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		int user_Id = loginUser.getId();

		CommentVO vo = new CommentVO();
		vo.setUser_Id(user_Id);
		vo.setCommunity_Id(community_Id);
		vo.setComment_Id(comment_Id);
		commentService.insertComment(vo);
		return ResponseEntity.ok("success");

	}

	// 커뮤니티 댓글 수정
	@PostMapping("/community/comment/update")
	public ResponseEntity<?> wuw(@RequestParam("id") int id, @RequestParam("community_Id") int community_Id,
			@RequestParam("comment_Id") String comment_Id, HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		int user_Id = loginUser.getId();

		CommentVO vo = commentService.selectOneComment(id);
		vo.setUser_Id(user_Id);
		vo.setComment_Id(comment_Id);
		vo.setCommunity_Id(community_Id);
		vo.setLikes(vo.getLikes());
		commentService.updateComment(vo);
		return ResponseEntity.ok("success");

	}

	// 커뮤니티 댓글 삭제
	@PostMapping("/community/comment/delete")
	public ResponseEntity<?> wwww(@RequestParam("id") int id, @RequestParam("community_Id") int community_Id,
			HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		int user_Id = loginUser.getId();

		CommentVO vo = commentService.selectOneComment(id);
		if (vo.getUser_Id() == user_Id || "admin".equals(loginUser.getRole())) {
			commentService.deleteComment(vo);
			return ResponseEntity.ok("success");
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
		}

	}

	// 좋아요 버튼 증가
	@PostMapping("/community/like")
	@ResponseBody
	public ResponseEntity<?> wwwww(@RequestParam("community_Id") int id, HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		int user_Id = loginUser.getId();

		CommunityVO vo = communityService.selectOneCommunity(id);
		vo.setUser_Id(user_Id);
		communityService.updatelikesplus(vo);
		// communityService.selectOneCommunity(id);
		return ResponseEntity.ok(communityService.selectOneCommunity(id).getLikes());

	}

	// 댓글 버튼 증가
	@PostMapping("/community/comment/like")
	@ResponseBody
	public ResponseEntity<?> likeComment(@RequestParam("id") int id, HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		int user_Id = loginUser.getId();

		CommentVO vo = commentService.selectOneComment(id);
		vo.setUser_Id(user_Id);
		commentService.updatelikesplus2(vo);
		// commentService.selectOneComment(id);

		return ResponseEntity.ok(commentService.selectOneComment(id).getLikes());

	}

}
