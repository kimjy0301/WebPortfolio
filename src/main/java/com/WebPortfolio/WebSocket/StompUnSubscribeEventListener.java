package com.WebPortfolio.WebSocket;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StompUnSubscribeEventListener implements ApplicationListener<SessionUnsubscribeEvent> {

	

	@Override
	public void onApplicationEvent(SessionUnsubscribeEvent sessionUnSubscribeEvent) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionUnSubscribeEvent.getMessage());
		
	}
}
