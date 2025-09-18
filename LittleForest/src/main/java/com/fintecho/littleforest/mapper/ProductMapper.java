package com.fintecho.littleforest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fintecho.littleforest.vo.ProductVO;


@Mapper
public interface ProductMapper {
	ProductVO getProductByProductId(int product_Id);
	
	List<ProductVO> getAllProductByMerchantId(int merchant_Id);
	
}