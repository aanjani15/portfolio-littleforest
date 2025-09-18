package com.fintecho.littleforest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fintecho.littleforest.mapper")  // 매퍼 인터페이스 위치 지정
public class Littleforset2Application {

	public static void main(String[] args) {
		SpringApplication.run(Littleforset2Application.class, args);
	}

}
