package com.WebPortfolio.WebSocket;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatService {
	
	
	public ArrayList<String> chatList = new ArrayList<String>();

	private final SimpMessagingTemplate template;
	
	@Autowired
	public ChatService(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	public void chatAdd(String name) {
		chatList.add(name);		
		Room test = new Room();		
		test.setRoomName(name);
		template.convertAndSend("/subscribe/chat/server", test);
	}
	
	public void chatRemove(String name) {
		chatList.remove(name);		
	}
	
	
	
	

}
