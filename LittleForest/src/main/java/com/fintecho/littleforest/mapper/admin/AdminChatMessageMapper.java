package com.fintecho.littleforest.mapper.admin;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.fintecho.littleforest.vo.admin.ChatMessageVO;

@Mapper
public interface AdminChatMessageMapper {
	
	ArrayList<ChatMessageVO> getAllChatMessage(int chat_Room_Id);
	String getLastMessageByChatRoomId(int chat_Room_Id);
	
	void insertChatMessage(ChatMessageVO message);
}
