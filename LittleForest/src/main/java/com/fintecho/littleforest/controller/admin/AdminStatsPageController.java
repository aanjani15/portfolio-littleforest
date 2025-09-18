package com.fintecho.littleforest.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminStatsPageController {
	@GetMapping("/admin/stats")
	public String statsPage() {
		return "admin/stats";
	}
}
