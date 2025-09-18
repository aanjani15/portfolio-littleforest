package com.fintecho.littleforest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

/** 포트원 토큰 발급 + 결제 조회 간단 유틸 */
@Component
public class PortOneClient {

    @Value("${iamport.imp_key}")
    private String apiKey;

    @Value("${iamport.imp_secret}")
    private String apiSecret;

    private final RestTemplate rest = new RestTemplate();

    /** 액세스 토큰 발급 */
    private String getAccessToken() {
        String url = "https://api.iamport.kr/users/getToken";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format("{\"imp_key\":\"%s\",\"imp_secret\":\"%s\"}", apiKey, apiSecret);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResp> resp = rest.exchange(url, HttpMethod.POST, entity, TokenResp.class);
        if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null && resp.getBody().getResponse() != null) {
            return resp.getBody().getResponse().getAccess_token();
        }
        throw new IllegalStateException("PORTONE_TOKEN_FAIL");
    }

    /** imp_uid로 결제 단건 조회 */
    public PortOnePayment getPayment(String impUid) {
        String token = getAccessToken();
        String url = "https://api.iamport.kr/payments/" + impUid;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<PaymentResp> resp = rest.exchange(url, HttpMethod.GET, entity, PaymentResp.class);
        if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null && resp.getBody().getResponse() != null) {
            return resp.getBody().getResponse();
        }
        throw new IllegalStateException("PORTONE_PAYMENT_NOT_FOUND");
    }

    /* ====== DTOs ====== */
    @Data static class TokenResp { TokenInner response; }
    @Data static class TokenInner { String access_token; }

    @Data static class PaymentResp { PortOnePayment response; }

    @Data
    public static class PortOnePayment {
        private String status; // paid, ready, cancelled ...
        private int amount;    // 실제 결제 금액
        private String imp_uid;
        private String merchant_uid;
    }
}

/*
  결제 결과는 클라이언트 신뢰 X. 
  서버가 포트원 REST로 1) 토큰 발급 → 2) imp_uid 조회 → status/amount 검증 후 DB 반영.
 */