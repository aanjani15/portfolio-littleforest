package com.fintecho.littleforest.controller.admin;

import java.util.Map;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.service.admin.ChatService;
import com.fintecho.littleforest.vo.admin.ChatMessageVO;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	private final SimpMessagingTemplate messagingTemplate;

	// 클라이언트가 /app/chat.send 로 보냄
	@MessageMapping("/chat.send")
	public void send(@Valid @Payload ChatMessageVO message) {
		chatService.save(message);

		// 구독자에게 전송: /topic/rooms/{roomId}
		messagingTemplate.convertAndSend("/topic/rooms/" + message.getChat_Room_Id(), message);
	}

	// 입장 이벤트(선택)
	@MessageMapping("/chat.enter")
	public void enter(@Valid @Payload ChatMessageVO message) {
		message.setType("ENTER");
		message.setContent(message.getSender() + "님이 입장했습니다.");
		send(message);
	}

	@PostMapping("/set-user")
	@ResponseBody
	public Map<String, Object> setUserId(@RequestParam("id") int id, HttpSession session) {
		session.setAttribute("user_Id", id);

		return Map.of("status", "ok", "user_Id", id);
	}

	// 최근 N개 메시지 요청(옵션: REST)
	// GET /rooms/{roomId}/recent?limit=50
	// 필요 시 @RestController로 분리 가능
}
