package com.fintecho.littleforest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.WalletMapper;
import com.fintecho.littleforest.vo.WalletVO;




@Service
public class WalletServiceImpl implements WalletService {

    private final WalletMapper walletMapper;

    @Autowired
    public WalletServiceImpl(WalletMapper walletMapper) {
        this.walletMapper = walletMapper;
    }

    @Override
    public List<WalletVO> getWalletsByUserId(int userId) {
        return walletMapper.findByUserId(userId);
    }

    @Override
    public WalletVO getWalletById(int id) {
        return walletMapper.findById(id);
    }

    @Override
    public void createWallet(WalletVO wallet) {
        walletMapper.insertWallet(wallet);
    }

    @Override
    public void updateWallet(WalletVO wallet) {
        walletMapper.updateWallet(wallet);
    }

    @Override
    public void deleteWallet(int id) {
        walletMapper.deleteWallet(id);
    }

}
