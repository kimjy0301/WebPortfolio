package com.WebPortfolio.WebSocket;

import java.util.LinkedList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SessionDisconnectedEventListener implements ApplicationListener<SessionDisconnectEvent> {

	

	@Autowired
	ChatService chatService;
		
	@Override
	public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());

		chatService.chatRemove(headerAccessor.getSessionId());		
		
		log.info(headerAccessor.toString());
	}
}
