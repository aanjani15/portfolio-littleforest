package com.fintecho.littleforest.mapper.admin;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.fintecho.littleforest.dto.ProductDTO;
import com.fintecho.littleforest.vo.ProductVO;

@Mapper
public interface AdminProductMapper {

	void insertProduct(ProductDTO productdto);
	
	ArrayList<ProductVO> getAllProduct();
	
	void updateProduct(ProductVO productvo);

	void deleteProduct(ProductVO productvo);
}