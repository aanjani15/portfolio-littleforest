package com.fintecho.littleforest.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintecho.littleforest.mapper.AttendanceMapper;
import com.fintecho.littleforest.vo.AttendanceVO;
import com.fintecho.littleforest.vo.UserVO;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private AttendanceMapper attendanceMapper;

	@Override
	public UserVO findByUserSeq(int id) {
		return attendanceMapper.findByUserSeq(id);
	}

	@Override
	public void insertAttendance(AttendanceVO vo) {
		attendanceMapper.insertAttendance(vo);
	}

	@Override
	public List<String> getAttendanceDates(int user_id) {
		return attendanceMapper.getAttendanceDates(user_id);
	}

	@Override
	public boolean hasAttendedToday(AttendanceVO vo) {
		return attendanceMapper.countTodayAttendance(vo) > 0;
	}

	@Override
	public int getTotalAttendanceCount(int user_id) {
		return attendanceMapper.getTotalAttendanceCount(user_id);
	}

	@Override
	@Transactional
	public void addAttendanceRewardToInventory(int user_id, int water, int biyro) {
		attendanceMapper.addAttendanceRewardToInventory(user_id, water, biyro);
	}

	@Override
	public Map<String, Object> getCurrentStocks(int user_id) {
		return attendanceMapper.selectCurrentStocks(user_id);
	}

}
