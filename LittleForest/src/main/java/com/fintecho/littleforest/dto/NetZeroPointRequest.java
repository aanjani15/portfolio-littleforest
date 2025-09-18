package com.fintecho.littleforest.dto;

import java.util.HashMap;
import java.util.Map;

import io.codef.api.EasyCodefUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 화면에서 받는 값(아이디/비밀번호/년도) → CODEF API 바디로 변환 - 내부 도메인(user_id)을 그대로 쓸 수도 있지만, 외부
 * API는 userId(카멜) 규격이므로 "경계에서만" 키 이름을 맞춰주는 것
 */

@Getter
@Setter
public class NetZeroPointRequest {

	// CODEF 요구: 기관코드(탄소중립포인트는 0002 고정)
	private String organization = "0002";

	// 외부 사이트(탄소중립포인트) 로그인용 아이디/비번
	private String userId; // NOTE: 외부 API 규격에 맞게 카멜케이스 사용
	private String password; // 평문으로 입력받음(화면) → 전송 전 RSA 암호화

	// 선택(미입력 시 최근연도)
	private String year;

	/**
	 * CODEF로 보낼 요청 바디 생성 - password는 반드시 RSA 암호화 필요 - publicKey는 CODEF 대시보드에서 발급받은
	 * 값
	 */
	public Map<String, Object> toCodefBody(String publicKey) throws Exception {
		Map<String, Object> body = new HashMap<>();
		body.put("organization", this.organization);
		body.put("userId", this.userId);

		// RSA 암호화: 평문 비밀번호 → 공개키로 암호화
		String encPw = EasyCodefUtil.encryptRSA(this.password, publicKey);
		body.put("password", encPw);

		if (this.year != null && !this.year.isBlank()) {
			body.put("year", this.year);
		}
		return body;
	}
}
