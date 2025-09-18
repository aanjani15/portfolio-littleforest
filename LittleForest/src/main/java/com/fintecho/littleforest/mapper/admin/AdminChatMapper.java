package com.fintecho.littleforest.mapper.admin;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.dto.ChatOtherUserDTO;
import com.fintecho.littleforest.dto.ChatRoomDTO;

@Mapper
public interface AdminChatMapper {

	String getChatRoomNameById(int id);

	String getLastMessageByChatRoomId(int chat_Room_Id);

	ArrayList<Integer> getOtherUserByUserId(int user_Id);

	ArrayList<ChatRoomDTO> getChatRoomDTO(int user_Id);

	ChatOtherUserDTO getOtherUserInfoById(int room_Id, int user_Id);

	int countChatRoom(int user_Id);

	void insertChatRoom(int user_Id);

	void insertChatRoomAdmin();

}
