package com.fintecho.littleforest.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.codef.api.EasyCodef;
import io.codef.api.EasyCodefServiceType;

/**
 * CODEF Java SDK 사용:
 * - setClientInfoForDemo(clientId, clientSecret)
 * - setPublicKey(publicKey)
 * - requestProduct(url, serviceType, body)  ← 내부에서 토큰 발급/재발급 포함
 */
@Service
public class CodefServiceImpl implements CodefService {

    private final EasyCodef codef;

    // application.properties에서 주입
    public CodefServiceImpl(
        @Value("${codef.demo-client-id}") String demoClientId,
        @Value("${codef.demo-client-secret}") String demoClientSecret,
        @Value("${codef.public-key}") String publicKey
    ) {
        this.codef = new EasyCodef();
        this.codef.setClientInfoForDemo(demoClientId, demoClientSecret); // 데모키 등록
        this.codef.setPublicKey(publicKey);                               // RSA용 공개키 등록
    }

    @Override
    public String getNetZeroPoint(Map<String, Object> body) throws Exception {
        // 제품 URL (문서 기준)
        String productUrl = "/v1/kr/etc/gp/netzero/point";

     // 여기! SANDBOX 쓰지 말고 DEMO(또는 운영이면 PRODUCTION)
        return codef.requestProduct(productUrl, EasyCodefServiceType.DEMO,
            new java.util.HashMap<>(body));
    }

    @Override
    public String getAccessTokenForDemo() throws Exception {
        return codef.requestToken(EasyCodefServiceType.DEMO);
    }
}