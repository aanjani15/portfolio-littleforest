package com.fintecho.littleforest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.fintecho.littleforest.mapper")
@EnableScheduling

public class LittleForestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LittleForestApplication.class, args);
	}

}

/*
 * @EnableScheduling 추가 09.02
 */
