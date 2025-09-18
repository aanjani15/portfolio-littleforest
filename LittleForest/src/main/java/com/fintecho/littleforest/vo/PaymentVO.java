package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentVO {
	private int id;
	private int wallet_Id;
	private int product_Id;
	private int amount;
	private String type;
	private String description;
	private Date tran_At;

	// JOIN 결과로 가져온 값 저장용
	private String bank_Name; // 조인된 wallet_table 컬럼
	private String nickname;

	// point_rule_table
	private int earnedPoint;
	// ← DB 컬럼 없음. SQL alias(earned_point)로 계산되어 매핑
	// 절감된 탄소량
	private java.math.BigDecimal savedEmission; // 이번 결제로 절감된 kg (NULL이면 0)

}
