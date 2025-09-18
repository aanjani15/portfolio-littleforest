package com.fintecho.littleforest.service.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintecho.littleforest.mapper.admin.AdminChatMapper;
import com.fintecho.littleforest.mapper.admin.AdminChatMessageMapper;
import com.fintecho.littleforest.vo.admin.ChatMessageVO;

@Service
public class ChatService {
	@Autowired
	private AdminChatMapper adminchatmapper;
	@Autowired
	private AdminChatMessageMapper adminchatmessagemapper;

  // roomId -> messages
  private final Map<String, List<ChatMessageVO>> store = new ConcurrentHashMap<>();

  public void save(ChatMessageVO m) {
	  
	  adminchatmessagemapper.insertChatMessage(m);
	  
		/*
		 * m.setTimestamp(LocalDateTime.now());
		 * store.computeIfAbsent(m.getChat_Room_Id(), k ->
		 * Collections.synchronizedList(new ArrayList<>())) .add(m);
		 */
  }

  public List<ChatMessageVO> last(String roomId, int limit) {
    List<ChatMessageVO> list = store.getOrDefault(roomId, Collections.emptyList());
    int size = list.size();
    if (size <= limit) return new ArrayList<>(list);
    return new ArrayList<>(list.subList(size - limit, size));
  }
}