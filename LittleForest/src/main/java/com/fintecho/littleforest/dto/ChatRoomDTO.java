package com.fintecho.littleforest.dto;

import lombok.Data;

@Data
public class ChatRoomDTO {
	private int id;
	private String name;
	private String last_Message;
	private String other_User_Nickname;
	private String other_User_Profile;
}
