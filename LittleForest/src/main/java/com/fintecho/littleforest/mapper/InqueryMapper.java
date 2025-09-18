package com.fintecho.littleforest.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.fintecho.littleforest.vo.InqueryVO;

@Mapper
public interface InqueryMapper {
//문의내용 전부 조회(관리자용이긴함)
ArrayList<InqueryVO> getAllInquery();
//사용자가 문의한 문의내용 전부 조회
ArrayList<InqueryVO> getUserInquery(@Param("user_Id") int user_Id);
//문의내용 하나 선택
InqueryVO getOneInquery(@Param("id") int id,@Param("user_Id") int user_Id);
//문의 등록
int insertInquery(InqueryVO ivo);
//문의 수정
int updateInquery(InqueryVO ivo);
//문의 삭제
int deleteInquery(InqueryVO ivo);
//문의 상태 수정
int updateStatus(InqueryVO ivo);
InqueryVO inqueryAnswer(@Param("user_Id") int user_Id);
}
