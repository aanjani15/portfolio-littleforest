package com.fintecho.littleforest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 응답 JSON을 보여주기 위한 최소 필드 매핑용 DTO - 모든 필드를 다 받을 필요는 없고, 화면에 보여줄 핵심만 추림 -
 * Jackson이 동일 키를 자동 매핑 VO로 안하는 이유는 sql과 구분위한 것 (써도 됨)
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetZeroPointResponse {
	private String resName;
	private String resRating;
	private String resCount;
	private String resEarnPoint;
	private String resGivePoint;
	private String resAvailablePoint;

	private List<MonthlyPoint> resPointList; // 월별 현황
	private List<TypePoint> resPointList1; // 실천항목별 포인트

	@Getter
	@Setter
	public static class MonthlyPoint {
		private String resYearMonth;
		private String resCount;
		private String resEarnPoint;
		private String resGivePlanPoint;
		private String resGivePoint;
		private String resAvailablePoint;
		private String resPaymentDate;
		private String resAvailablePoint1;
		private List<Detail> resDetailList; // 상세 실적
	}

	@Getter
	@Setter
	public static class Detail {
		private String resDate;
		private String resCompanyNm;
		private String resAction;
		private String resDetail;
		private String resCount;
		private String resActPoint;
	}

	@Getter
	@Setter
	public static class TypePoint {
		private String resType; // 예: "전자영수증", "무공해차 대여 ..."
		private String resCount;
		private String resPerformance;
		private String resEarnPoint;
	}
}