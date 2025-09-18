package com.fintecho.littleforest.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ChatMessageDTO {
	
	private String sender;
	private String content;
	private LocalDateTime timestamp;
	
}
