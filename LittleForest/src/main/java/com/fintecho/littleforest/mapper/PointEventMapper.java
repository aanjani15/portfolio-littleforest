package com.fintecho.littleforest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PointEventMapper {

	int insert(@Param("user_Id") int userId, @Param("event_Code") String eventCode, @Param("amount") int amount,
			@Param("memo") String memo);

	int countToday(@Param("user_Id") int userId, @Param("event_Code") String eventCode);
}