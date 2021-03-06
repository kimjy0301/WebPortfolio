package com.WebPortfolio.WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {

	private final SimpMessagingTemplate template;

	@Autowired
	ChatService chatService;

	@Autowired
	public ChatMessageController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/chat/join")
	public void join(ChatMessage message) {
		message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
		template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
	}

	@MessageMapping("/chat/message/{id}")
	public void room(@DestinationVariable String id, ChatMessage message) {
		
		template.convertAndSend("/subscribe/chat/room/" + id, message);
	}

	@MessageMapping("/chat/create")
	public void create(Room room) {
		// chatservice 에서 보냄
		// template.convertAndSend("/subscribe/chat/create", room);
	}

	@MessageMapping("/chat/delete")
	public void delete(Room room) {
		// chatservice 에서 보냄
		// template.convertAndSend("/subscribe/chat/delete", room);
	}

}