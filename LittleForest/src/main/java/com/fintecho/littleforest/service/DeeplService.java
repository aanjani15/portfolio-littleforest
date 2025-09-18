package com.fintecho.littleforest.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeeplService {
	
	private final WebClient deeplWebClient;     // SecurityConfig에서 만든 빈 주입
    @Value("${deepl.apiKey}") 
    private String apiKey;

    /**
     * HTML 블록을 태그 구조를 보존하여 번역
     * @param html   번역할 HTML 조각(outerHTML)
     * @param source 원문 언어 (예: "KO")
     * @param target 목표 언어 (예: "EN-US", "JA", "ZH")
     */
    
    public Mono<String> translateHtml(String html, String source, String target) {
        return deeplWebClient.post()
            .uri("/v2/translate")
            .header("Authorization", "DeepL-Auth-Key " + apiKey) // DeepL 인증
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters
                .fromFormData("text", html) // 번역 대상 HTML
                .with("source_lang", source.toUpperCase()) // "KO"(기본설정)
                .with("target_lang", target.toUpperCase()) // "EN-US" / "JA" / "ZH"
                .with("tag_handling", "html"))  // 태그 보존 옵션
            .retrieve()
            .bodyToMono(Map.class)
            .map(res -> {
            	 // 응답 예: { "translations": [ { "text": "<번역된 HTML>" } ] }
                var arr = (java.util.List<Map<String,Object>>) res.get("translations");
                return String.valueOf(arr.get(0).get("text"));
            });
    }

}
