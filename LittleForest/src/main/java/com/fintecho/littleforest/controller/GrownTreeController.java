package com.fintecho.littleforest.controller;

import java.util.ArrayList;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fintecho.littleforest.service.GrowingTreeService;
import com.fintecho.littleforest.service.GrownTreeService;
import com.fintecho.littleforest.service.StockService;
import com.fintecho.littleforest.vo.GrowingTreeVO;
import com.fintecho.littleforest.vo.GrownTreeVO;
import com.fintecho.littleforest.vo.StockVO;

import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class GrownTreeController {

	@Autowired
	private GrownTreeService grownTreeService;

	@Autowired
	private GrowingTreeService growingTreeService;

	@Autowired
	private StockService stockService;

	@GetMapping("/growtree/grown")
	public String growntree(ArrayList<GrownTreeVO> vo, Model model, HttpSession session) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}

		int user_Id = loginUser.getId();

		StockVO svo = stockService.getSaveStock(user_Id);

		if (svo != null) {
			Date d1 = svo.getBiyro_Used_At();
			if (d1 != null) {
				Date d2 = new Date(d1.getTime() + 3L * 24 * 60 * 60 * 1000);
				model.addAttribute("after3", d2);
			}
			model.addAttribute("stock", svo);
		} else {
			model.addAttribute("stock", new StockVO());
		}

		vo = grownTreeService.grownSelect(user_Id);
		model.addAttribute("tree", vo);

		return "grown";
	}

	@PostMapping("/growtree/more")
	public ResponseEntity<String> more(@RequestParam(value = "screenshot", required = false) String photo,
			HttpSession session) {
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.badRequest().body("login required");
		}

		int user_Id = loginUser.getId();
		GrowingTreeVO treeVO = growingTreeService.getAllStock(user_Id);
		if (treeVO == null) {
			return ResponseEntity.status(500).body("tree not found");
		}

		GrownTreeVO grownvo = new GrownTreeVO();
		grownvo.setUser_Id(user_Id);
		grownvo.setGrowing_Tree_Id(treeVO.getId());
		grownvo.setPhoto(photo);

		System.out.println("[grownInsert] userId=" + grownvo.getUser_Id() + ", growingTreeId="
				+ grownvo.getGrowing_Tree_Id() + ", photo=" + grownvo.getPhoto());

		try {
			grownTreeService.grownInsert(grownvo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("DB insert error: " + e.getMessage());
		}

		return ResponseEntity.ok("success");
	}

}
