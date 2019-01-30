package com.WebPortfolio.WebSocket;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatService {

	public HashMap<String, String> chatMap = new HashMap<String, String>();

	private final SimpMessagingTemplate template;

	@Autowired
	public ChatService(SimpMessagingTemplate template) {
		this.template = template;
	}

	public void chatAdd(String sessionId) {

		chatMap.put(sessionId, "");

	}

	public void chatRemove(String sessionId) {

		if (chatMap.containsKey(sessionId)) {
			chatMap.remove(sessionId);

			Room room = new Room();
			room.setSessionId(sessionId);

			template.convertAndSend("/subscribe/chat/delete", room);
		}

	}

	public void subsRoom(String sessionId, String des) {
		chatMap.put(sessionId, des);

		Room room = new Room();
		room.setRoomName(des);
		room.setSessionId(sessionId);

		template.convertAndSend("/subscribe/chat/create", room);
	}

	public HashMap<String, String> getChat() {
		return chatMap;
	}
}
