package com.fintecho.littleforest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fintecho.littleforest.vo.WalletVO;

@Mapper
public interface WalletMapper {

	 // 특정 사용자(user_id)의 지갑 목록 조회
    List<WalletVO> findByUserId(int userId);

    // 지갑 1개 조회
    WalletVO findById(int id);

    // 지갑 추가
    int insertWallet(WalletVO wallet);

    // 지갑 정보 업데이트
    int updateWallet(WalletVO wallet);

    // 지갑 삭제
    int deleteWallet(int id);
    
    void insertWallet(int user_Id, String bankName, String account_Number);
    
    void addBalance(int user_Id, String bankName, int amount);

    void desBalance(int user_Id, int wallet_Id, int amount);

}
