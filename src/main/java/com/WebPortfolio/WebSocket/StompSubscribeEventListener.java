package com.WebPortfolio.WebSocket;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StompSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {

	@Autowired
	ChatService chatService;

	@Override
	public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {

		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());

		String simpSessionId = headerAccessor.getSessionId();
		String destination = headerAccessor.getDestination();

		@SuppressWarnings("rawtypes")
		LinkedMultiValueMap generic = (LinkedMultiValueMap) headerAccessor.getHeader("nativeHeaders");
		@SuppressWarnings("unchecked")
		LinkedList<String> linkedlist = (LinkedList<String>) generic.get("callUser");

		if (linkedlist != null) {
			String user = linkedlist.getFirst();
			if (!user.equals("server")) {
				// 클라이언트 접속만 구독 추가
				log.info("simpSessionId = " + simpSessionId + "destination = " + destination);
				chatService.subsRoom(simpSessionId, destination);
			}
		}


	}
}
