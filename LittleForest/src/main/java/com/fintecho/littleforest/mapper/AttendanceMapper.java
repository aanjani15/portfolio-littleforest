package com.fintecho.littleforest.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.vo.AttendanceVO;
import com.fintecho.littleforest.vo.UserVO;

@Mapper
public interface AttendanceMapper {

	UserVO findByUserSeq(@Param("id") int id); 

	void insertAttendance(AttendanceVO attendance);

	List<String> getAttendanceDates(@Param("user_Id") int user_id); 

	int countTodayAttendance(AttendanceVO vo);

	int getTotalAttendanceCount(@Param("user_Id") int user_id); 

	int addAttendanceRewardToInventory(@Param("user_Id") int user_id, @Param("water") int water,
			@Param("biyro") int biyro);

	Map<String, Object> selectCurrentStocks(@Param("user_Id") int user_id);
}
