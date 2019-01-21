package com.WebPortfolio.WebSocket;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatService {
	
	
	public ArrayList<String> chatList = new ArrayList<String>();
	public HashMap<String,String> chatMap = new HashMap<String,String>();
	

	private final SimpMessagingTemplate template;
	
	@Autowired
	public ChatService(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	public void chatAdd(String sessionId) {
		chatList.add(sessionId);		
		
		chatMap.put(sessionId, "");
		
	}
	
	public void chatRemove(String sessionId) {
		chatList.remove(sessionId);		
		chatMap.remove(sessionId);
		
		Room test = new Room();		
		test.setRoomName(sessionId);
		
		template.convertAndSend("/subscribe/chat/delete", test);
	}
	public void subsRoom(String sessionId,String des) {
		chatMap.put(sessionId, des);

		Room test = new Room();		
		test.setRoomName(sessionId);
		template.convertAndSend("/subscribe/chat/create", test);
	}
	
	
	public HashMap<String,String> getChat() {
		return chatMap;
	}	
}
