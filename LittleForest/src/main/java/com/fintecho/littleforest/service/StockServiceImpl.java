package com.fintecho.littleforest.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.StockMapper;
import com.fintecho.littleforest.vo.StockVO;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private StockMapper stockMapper;

	@Override
	public StockVO getSaveStock(int user_Id) {
		return stockMapper.getSaveStock(user_Id);
	}

}
