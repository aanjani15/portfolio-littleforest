package com.fintecho.littleforest.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.fintecho.littleforest.vo.StockVO;

@Mapper
public interface StockMapper {
//사용자 재고 기록 조회
StockVO getSaveStock(int user_Id);
//다 키운 재고 생성
int insertStock(StockVO svo);
//다 키운 재고를 업데이트(더미데이터는 생성하되 업데이트는 된다.(이러면 id로 조회해야하잖아.))
int updateStock(StockVO svo);

StockVO getOneSaveStock(@Param("user_Id") int user_Id);

}
