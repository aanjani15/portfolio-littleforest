package com.fintecho.littleforest.service;

import java.util.List;
import java.util.Map;

import com.fintecho.littleforest.vo.AttendanceVO;
import com.fintecho.littleforest.vo.UserVO;

public interface AttendanceService {

	UserVO findByUserSeq(int id);

	void insertAttendance(AttendanceVO vo);

	List<String> getAttendanceDates(int user_id);

	boolean hasAttendedToday(AttendanceVO vo);

	int getTotalAttendanceCount(int user_id);

	void addAttendanceRewardToInventory(int user_id, int water, int biyro);

	Map<String, Object> getCurrentStocks(int user_id);

}
