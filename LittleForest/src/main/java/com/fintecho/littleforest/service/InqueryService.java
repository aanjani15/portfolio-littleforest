package com.fintecho.littleforest.service;

import java.util.ArrayList;

import com.fintecho.littleforest.vo.InqueryVO;

public interface InqueryService {
	// 문의내용 전부 조회(관리자용이긴함)
	ArrayList<InqueryVO> getAllInquery();

	// 사용자가 문의한 문의내용 전부 조회
	ArrayList<InqueryVO> getUserInquery(int user_Id);

	// 문의내용 하나 선택
	InqueryVO getOneInquery(int id, int user_Id);

	// 문의 등록
	int insertInquery(InqueryVO ivo);

	// 문의 수정
	int updateInquery(InqueryVO ivo);

	// 문의 삭제
	int deleteInquery(InqueryVO ivo);

	// 문의 상태 수정
	int updateStatus(InqueryVO ivo);

	InqueryVO inqueryAnswer(int user_Id);
}
