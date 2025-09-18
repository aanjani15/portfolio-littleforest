package com.fintecho.littleforest.service;

import java.util.List;

import com.fintecho.littleforest.vo.WalletVO;

public interface WalletService {
	
	 List<WalletVO> getWalletsByUserId(int userId);

	    WalletVO getWalletById(int id);

	    void createWallet(WalletVO wallet);

	    void updateWallet(WalletVO wallet);

	    void deleteWallet(int id);

}
