package com.fintecho.littleforest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.PaymentMapper;
import com.fintecho.littleforest.vo.PaymentVO;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentMapper paymentMapper;

	@Override
	public List<PaymentVO> getPaymentListByUserId(int userId) {
		return paymentMapper.getPaymentListByUserId(userId);
	}

	@Override
	public List<PaymentVO> getPaymentListWithPoints(int userId) {
		return paymentMapper.selectPaymentWithPoints(userId);
	}
}
