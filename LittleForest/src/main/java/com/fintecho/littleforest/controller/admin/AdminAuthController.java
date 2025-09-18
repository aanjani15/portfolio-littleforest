package com.fintecho.littleforest.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminAuthController {

	@GetMapping("/admin/admin-login")
	public String loginPage() {
		return "admin/admin-login";
	}
}
