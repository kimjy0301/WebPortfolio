package com.WebPortfolio.WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class StompSessionConnectedEventListener implements ApplicationListener<SessionConnectedEvent> {
	
	
	
	@Autowired
	ChatService chatService;
		
	
	@Override
	public void onApplicationEvent(SessionConnectedEvent sessionConnectedEvent) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionConnectedEvent.getMessage());
		
		chatService.chatAdd(headerAccessor.getSessionId());
		
		log.info(headerAccessor.toString());
	}
}
