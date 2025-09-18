package com.fintecho.littleforest.vo.admin;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ChatMessageVO {
	private String type;
	private String chat_Room_Id;
	private String sender;
	private String content;
	private LocalDateTime timestamp;
	
}
