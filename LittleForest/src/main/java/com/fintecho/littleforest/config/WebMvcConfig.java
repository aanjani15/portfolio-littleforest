package com.fintecho.littleforest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* 0903 추가 (sidebar image) */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    // 업로드 파일 URL: /uploads/**  -> 실제 디렉터리: /var/app/uploads/
	    registry.addResourceHandler("/uploads/**")
	            .addResourceLocations("file:/var/app/uploads/")
	            .setCachePeriod(3600);
	  }
}
