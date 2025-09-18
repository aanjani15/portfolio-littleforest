package com.fintecho.littleforest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintecho.littleforest.service.DeeplService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor                    // final 필드에 대한 생성자 자동 생성
@RequestMapping("/i18n")                    // JS에서 /i18n/block 호출하므로 클래스 레벨 매핑
public class TranslateController {
	
	private final DeeplService deeplService;
	
	// 요청 바디 모델
    public record Req(String html, String source, String target) {}
	/**
     * HTML 블록 번역 엔드포인트
     * 프런트에서 보내준 HTML 조각을 DeepL로 번역해 그대로 돌려줌
     */
    @PostMapping(value = "/block", produces = "text/html; charset=UTF-8")
    public Mono<String> translateBlock(@RequestBody Req req) {
        String html   = req.html();
        String source = req.source() != null ? req.source() : "KO";
        String target = req.target() != null ? req.target() : "EN-US";
        return deeplService.translateHtml(html, source, target);
    }
}
