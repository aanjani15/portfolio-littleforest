package com.fintecho.littleforest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.mapper.MerchantMapper;
import com.fintecho.littleforest.mapper.PaymentMapper;
import com.fintecho.littleforest.mapper.ProductMapper;
import com.fintecho.littleforest.mapper.WalletMapper;
import com.fintecho.littleforest.vo.PaymentVO;
import com.fintecho.littleforest.vo.ProductVO;
import com.fintecho.littleforest.vo.WalletVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProductController {

	@Autowired
	ProductMapper productmapper;
	@Autowired
	PaymentMapper paymentmapper;
	@Autowired
	MerchantMapper merchantmapper;
	@Autowired
	WalletMapper walletmapper;

	@GetMapping("/product")
	public String product(@RequestParam int product_Id, Model model, HttpSession session) {

		int id = (int) session.getAttribute("user_Id");

		ProductVO product = productmapper.getProductByProductId(product_Id);
		String merchant_Name = merchantmapper.getMerchantNameByMerchantId(product.getMerchants_Id());
		List<WalletVO> wallet = walletmapper.findByUserId(id);
		int total_Balance = 0;

		for (WalletVO w : wallet) {
			total_Balance += w.getAccount_Balance();

		}
		Double point = product.getPrice() * (product.getCarbon_Effect() / 100);
		String pointText = String.format("%.2f", point);

		model.addAttribute("product", product);

		model.addAttribute("merchant_Name", merchant_Name);
		model.addAttribute("point", pointText);
		model.addAttribute("total_Balance", total_Balance);
		model.addAttribute("wallet", wallet);

		return "product/product_view";
	}
	
	@PostMapping("/product/payment/complete")
	@ResponseBody
	public ResponseEntity<String> chargeComplete(@RequestParam("wallet_Id") int wallet_Id
											, @RequestParam("amount") int amount 
											, @RequestParam("product_Id") int product_Id
											, HttpSession session) {

		PaymentVO paymentvo = new PaymentVO();
		
		paymentvo.setAmount(amount);
		paymentvo.setProduct_Id(product_Id);
		paymentvo.setWallet_Id(wallet_Id);
		
		paymentmapper.InsertPaymentByShop(paymentvo);
		
		return ResponseEntity.ok(String.valueOf(1));
	}
	
}