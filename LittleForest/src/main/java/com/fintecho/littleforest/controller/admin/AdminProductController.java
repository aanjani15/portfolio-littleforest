package com.fintecho.littleforest.controller.admin;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.dto.ProductDTO;
import com.fintecho.littleforest.mapper.admin.AdminProductMapper;
import com.fintecho.littleforest.vo.ProductVO;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

	@Autowired
	private AdminProductMapper adminproductmapper;
	
	@GetMapping
	public String product(Model model) {
			
		return "admin/product";
	}
	
	@GetMapping("/product_update")  
	public String update(Model model) {
		
		ArrayList<ProductVO> productvo = new ArrayList<>();
		productvo = adminproductmapper.getAllProduct();
		
		model.addAttribute("product", productvo);
		
		return "admin/product_update";
	}
	

	@PostMapping("/make")
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody ProductDTO productdto) {
		adminproductmapper.insertProduct(productdto);
		
		return ResponseEntity.ok(Map.of("id", "1"));
    }
	
	@PostMapping("/updater")
	@ResponseBody
	public void updater(@RequestBody ProductVO productvo) {
		adminproductmapper.updateProduct(productvo);
	}

	@PostMapping("/deleter")
	@ResponseBody
	public void deleter(@RequestBody ProductVO productvo) {
		
		adminproductmapper.deleteProduct(productvo);
		
	}
}
