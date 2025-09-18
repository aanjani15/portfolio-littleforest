package com.fintecho.littleforest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fintecho.littleforest.mapper.MerchantMapper;
import com.fintecho.littleforest.mapper.ProductMapper;
import com.fintecho.littleforest.mapper.UserMapper;
import com.fintecho.littleforest.mapper.WalletMapper;
import com.fintecho.littleforest.vo.ProductVO;
import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ShopController {

	@Autowired
	ProductMapper productmapper;
	@Autowired
	MerchantMapper merchantmapper;
	@Autowired
	WalletMapper walletmapper;
	@Autowired
	UserMapper userMapper;

	@GetMapping("/shop")
	public String product(Model model,HttpSession session) {
		
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}
		
		List<ProductVO> products = productmapper.getAllProductByMerchantId(4);

		model.addAttribute("products", products);

		return "shop/shopmain";
	}
}