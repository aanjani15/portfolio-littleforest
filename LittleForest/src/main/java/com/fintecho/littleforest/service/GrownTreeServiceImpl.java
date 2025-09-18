package com.fintecho.littleforest.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintecho.littleforest.mapper.GrowingTreeMapper;
import com.fintecho.littleforest.mapper.GrownTreeMapper;
import com.fintecho.littleforest.mapper.StockMapper;
import com.fintecho.littleforest.vo.GrowingTreeVO;
import com.fintecho.littleforest.vo.GrownTreeVO;
import com.fintecho.littleforest.vo.StockVO;

@Service
public class GrownTreeServiceImpl implements GrownTreeService {

	@Autowired
	private GrownTreeMapper grownTreeMapper;

	@Autowired
	private GrowingTreeMapper growingTreeMapper;

	@Autowired
	private StockMapper stockMapper;

	public ArrayList<GrownTreeVO> grownSelect(int user_Id) {
		// TODO Auto-generated method stub
		return grownTreeMapper.grownSelect(user_Id);
	}

	/*
	 * @Transactional public void grownInsert(GrownTreeVO grownvo) {
	 * 
	 * int user_Id = grownvo.getUser_Id();
	 * 
	 * GrowingTreeVO deletevo = growingTreeMapper.getAllStock(user_Id);
	 * 
	 * StockVO svo = new StockVO(); StockVO difference =
	 * stockMapper.getOneSaveStock(user_Id);
	 * 
	 * int biyro = deletevo.getBiyro_Stock(); int water = deletevo.getWater_Stock();
	 * Date used = deletevo.getBiyro_Used_At(); if (biyro != 0 || water != 0 || used
	 * != null) { svo.setUser_Id(user_Id); svo.setBiyro_Stock(biyro);
	 * svo.setBiyro_Used_At(used); svo.setWater_Stock(water);
	 * 
	 * } if (user_Id != difference.getUser_Id()) { stockMapper.insertStock(svo); }
	 * else { stockMapper.updateStock(svo); }
	 * growingTreeMapper.deletetree(deletevo); grownTreeMapper.grownInsert(grownvo);
	 * 
	 * }
	 */

	@Transactional
	public void grownInsert(GrownTreeVO grownvo) {
		int user_Id = grownvo.getUser_Id();

		GrowingTreeVO deletevo = growingTreeMapper.getAllStock(user_Id);

		StockVO svo = new StockVO();
		StockVO difference = stockMapper.getOneSaveStock(user_Id);

		int biyro = deletevo.getBiyro_Stock();
		int water = deletevo.getWater_Stock();
		Date used = deletevo.getBiyro_Used_At();

		if (biyro != 0 || water != 0 || used != null) {
			svo.setUser_Id(user_Id);
			svo.setBiyro_Stock(biyro);
			svo.setBiyro_Used_At(used);
			svo.setWater_Stock(water);
		}

		// ✅ 차이점: difference == null 먼저 체크
		if (difference == null) {
			stockMapper.insertStock(svo);
			System.out.println("[grownInsert] 신규 Stock insert userId=" + user_Id);
		} else {
			stockMapper.updateStock(svo);
			System.out.println("[grownInsert] 기존 Stock update userId=" + user_Id);
		}

		growingTreeMapper.deletetree(deletevo);
		grownTreeMapper.grownInsert(grownvo);
	}

}
