package com.fintecho.littleforest.mapper;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ShopMapper {
	String getShopById(int merchant_Id);
	
}