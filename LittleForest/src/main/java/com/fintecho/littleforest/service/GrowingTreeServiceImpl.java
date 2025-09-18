package com.fintecho.littleforest.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintecho.littleforest.mapper.GrowingTreeMapper;
import com.fintecho.littleforest.mapper.GrownTreeMapper;
import com.fintecho.littleforest.mapper.PointMapper;
import com.fintecho.littleforest.mapper.StockMapper;
import com.fintecho.littleforest.mapper.UserMapper;
import com.fintecho.littleforest.vo.GrowingTreeVO;
import com.fintecho.littleforest.vo.PointVO;
import com.fintecho.littleforest.vo.StockVO;

@Service
public class GrowingTreeServiceImpl implements GrowingTreeService {

	@Autowired
	private GrowingTreeMapper growingTreeMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private GrownTreeMapper grownTreeMapper;

	@Autowired
	private PointMapper pointMapper;

	@Autowired
	private StockMapper stockMapper;

	@Override
	public GrowingTreeVO getAllStock(int user_Id) {
		return growingTreeMapper.getAllStock(user_Id);
	}

	@Override
	public boolean ifTree(int user_Id) {
		// TODO Auto-generated method stub
		return growingTreeMapper.ifTree(user_Id) > 0;
	}

	@Transactional
	public int insertTree(GrowingTreeVO treevo) {
		int user_Id = treevo.getUser_Id();
		if (!ifTree(treevo.getUser_Id())) {
			StockVO svo = stockMapper.getOneSaveStock(user_Id);
			if(svo==null) {
				return growingTreeMapper.insertTree(treevo);
			}
			int biyro = svo.getBiyro_Stock();
			int water = svo.getWater_Stock();
			Date used = svo.getBiyro_Used_At();
			if (biyro != 0 || water != 0 || used != null) {
				treevo.setBiyro_Stock(biyro);
				treevo.setBiyro_Used_At(used);
				treevo.setWater_Stock(water);
				svo.setBiyro_Stock(0);
				svo.setBiyro_Used_At(null);
				svo.setWater_Stock(0);
				stockMapper.updateStock(svo);
				return growingTreeMapper.insertStockByUserId(treevo);
			}
			return growingTreeMapper.insertTree(treevo);
		} else {
			return 0;
		}
	}

	@Transactional
	public void updatestock(int quantity, int user_Id, int totalprice) {
		PointVO pvo = new PointVO();
		pvo.setUser_Id(user_Id);
		pvo.setAmount(totalprice);
		growingTreeMapper.updatestock(quantity, user_Id); // 개수 증가
		userMapper.updatepoint(totalprice, user_Id); // 포인트 차감
		pointMapper.biryocost(pvo);// 포인트 테이블에 거래내역으로 감

	}

	@Override
	public void updatews(int user_Id) {
		GrowingTreeVO treevo = growingTreeMapper.getAllStock(user_Id);
		int stock = treevo.getWater_Stock();
		if (stock <= 3) {
			growingTreeMapper.updatews(user_Id);
		}
	}

	@Transactional
	public void updatewater(int user_Id) {
		GrowingTreeVO treevo = growingTreeMapper.getAllStock(user_Id);
		int tree_Level = treevo.getTree_Level();
		int stock = treevo.getWater_Stock();
		Date used = treevo.getBiyro_Used_At();
		if (stock != 0) {
			if (used != null) {
				long d1 = new Date().getTime() - used.getTime();
				long d2 = 3L * 24 * 60 * 60 * 1000;
				if (d1 <= d2) {
					growingTreeMapper.updateburning(user_Id);
				} else
					growingTreeMapper.updatewater(user_Id);
			} else {
				growingTreeMapper.updatewater(user_Id);
			}
			System.out.println("물 재고:" + stock);
			treevo = growingTreeMapper.getAllStock(user_Id);
			updatelevel(tree_Level, user_Id);
		}
	}

	public void updatelevel(int tree_Level, int user_Id) {
		GrowingTreeVO treevo = growingTreeMapper.getAllStock(user_Id);
		int exp = treevo.getWater_Count();

		int needexp[] = { 0, 5, 20, 50, 80, 130, 200 };
		int newLevel = tree_Level; // 파라미터 받은 기존 레벨

		for (int i = 0; i < needexp.length; i++) {
			if (exp >= needexp[i]) {
				newLevel = i + 1;
			} else {
				break;
			}
		}

		// 레벨이 달라졌으면 DB 업데이트
		if (newLevel != tree_Level) {
			growingTreeMapper.updatelevel(newLevel, user_Id);
		}
		System.out.println("현재레벨 : " + newLevel);
		System.out.println("현재경험치 : " + exp);
	}

	@Override
	public void updatebiyro(int biyro_Stock, int user_Id) {
		// TODO Auto-generated method stub
		GrowingTreeVO treevo = growingTreeMapper.getAllStock(user_Id);
		int biyro = treevo.getBiyro_Stock();
		if (biyro > 0) {
			growingTreeMapper.updatebiyro(biyro_Stock, user_Id);
		} else {
			return;
		}
	}

}
