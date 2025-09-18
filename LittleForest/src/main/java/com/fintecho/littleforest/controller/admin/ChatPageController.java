package com.fintecho.littleforest.controller.admin;

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
public class ChatPageController {

	@Autowired
	private AdminChatMapper adminchatmapper;
	@Autowired
	private AdminChatMessageMapper adminchatmessagemapper;

	@GetMapping("/admin/chat")
	public String chat(HttpSession session, Model model) {

		int id = (int) session.getAttribute("user_Id");

		ArrayList<ChatRoomDTO> chatroomdto = new ArrayList<>();
		chatroomdto = adminchatmapper.getChatRoomDTO(id);

		model.addAttribute("chatroom", chatroomdto);
		model.addAttribute("title", "관리자 채팅");
		model.addAttribute("page", "chat"); // 사이드바 active에 쓰면 좋음

		return "admin/chat";
	}

	@GetMapping("/admin/chatroom")
	public String page(@RequestParam(value = "roomId", required = false, defaultValue = "0") Integer roomId,
			HttpSession session, Model model) {

		if (session.getAttribute("user_Id") == null) {
			session.setAttribute("user_Id", 2);
		}

		int id = (int) session.getAttribute("user_Id");

		ArrayList<ChatRoomDTO> chatroomdto = new ArrayList<>();
		ArrayList<ChatMessageVO> chatmessagevo = new ArrayList<>();
		ChatOtherUserDTO otheruser = new ChatOtherUserDTO();

		chatroomdto = adminchatmapper.getChatRoomDTO(id);

		if (chatroomdto != null && !chatroomdto.isEmpty()) {
			if (roomId == 0) {
				chatmessagevo = adminchatmessagemapper.getAllChatMessage(chatroomdto.get(0).getId());
				otheruser = adminchatmapper.getOtherUserInfoById(chatroomdto.get(0).getId(), id);
			} else {
				chatmessagevo = adminchatmessagemapper.getAllChatMessage(roomId);
				otheruser = adminchatmapper.getOtherUserInfoById(roomId, id);
			}
			model.addAttribute("chatroom", chatroomdto);
			model.addAttribute("chat", chatmessagevo);
			model.addAttribute("otheruser", otheruser);
		}

		model.addAttribute("title", "관리자 채팅");
		model.addAttribute("page", "chat"); // 사이드바 active에 쓰면 좋음
		return "admin/chat";

	}

}
