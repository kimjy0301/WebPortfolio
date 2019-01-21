package com.WebPortfolio.WebSocket;

import java.util.LinkedList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
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

		GenericMessage<?> generic = (GenericMessage<?>) headerAccessor.getHeader("simpConnectMessage");
		@SuppressWarnings("unchecked")
		Map<String, LinkedList<String>> nativeHeaders = (Map<String, LinkedList<String>>) generic.getHeaders()
				.get("nativeHeaders");
		LinkedList<String> linkedlist = nativeHeaders.get("callUser");
		if (linkedlist != null) {
			String user = linkedlist.getFirst();
			if (!user.equals("server")) {
				// 클라이언트 접속만 채팅방 추가
				chatService.chatAdd(headerAccessor.getSessionId());
			}
		}

		log.info(headerAccessor.toString());
	}
}
