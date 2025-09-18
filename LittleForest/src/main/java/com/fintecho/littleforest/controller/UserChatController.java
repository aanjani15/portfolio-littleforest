package com.fintecho.littleforest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.dto.ChatOtherUserDTO;
import com.fintecho.littleforest.dto.ChatRoomDTO;
import com.fintecho.littleforest.mapper.admin.AdminChatMapper;
import com.fintecho.littleforest.mapper.admin.AdminChatMessageMapper;
import com.fintecho.littleforest.vo.admin.ChatMessageVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserChatController {

	@Autowired
	private AdminChatMapper adminchatmapper;
	@Autowired
	private AdminChatMessageMapper adminchatmessagemapper;



	@PostMapping("/newchat")
	@ResponseBody
	public void newchat(@RequestParam(value = "roomId", required = false, defaultValue = "0") Integer roomId,
			HttpSession session, Model model) {


		int id = (int) session.getAttribute("user_Id");
		adminchatmapper.insertChatRoom(id);
		adminchatmapper.insertChatRoomAdmin();

	}

	@GetMapping("/chat/user/chatroomcount")
	@ResponseBody
	public int chatuser(HttpSession session, Model model) {

		int id = (int) session.getAttribute("user_Id");

		return adminchatmapper.countChatRoom(id);

	}

	@GetMapping("getchatroomid")
	@ResponseBody
	public int getchatroomid(HttpSession session) {
		Integer id = (Integer) session.getAttribute("user_Id");
		if (id == null)
			return 0;
		return adminchatmapper.getChatRoomDTO(id).get(0).getId();
	}

	@GetMapping("/chat/data")
	@ResponseBody
	public Map<String, Object> chatData(@RequestParam int roomId, HttpSession session) {

		
	    Integer myId = (Integer) session.getAttribute("user_Id");

		var chatRoomId = adminchatmapper.getChatRoomDTO(myId).get(0).getId();
		var other = adminchatmapper.getOtherUserInfoById(roomId, myId);
		var chat = adminchatmessagemapper.getAllChatMessage(roomId);

		Map<String, Object> res = new HashMap<>();
		res.put("chatRoomId", chatRoomId);
		res.put("myId", myId);
		res.put("otheruser", other); // { other_User_Name, other_User_Profile }
		res.put("chat", chat); // [{ sender, content, ... }]
		return res;
	}

}
