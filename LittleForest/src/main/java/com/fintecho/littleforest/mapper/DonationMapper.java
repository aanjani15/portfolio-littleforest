package com.fintecho.littleforest.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.fintecho.littleforest.vo.DonationVO;

@Mapper
	public interface DonationMapper {
	    int insertDonation(DonationVO vo);
	}

