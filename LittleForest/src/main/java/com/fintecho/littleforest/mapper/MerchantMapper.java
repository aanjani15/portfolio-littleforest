package com.fintecho.littleforest.mapper;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MerchantMapper {
	String getMerchantNameByMerchantId(int merchant_Id);
	
}