package com.WebPortfolio.Service;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WebPortfolio.Model.Message;
import com.WebPortfolio.Model.Visitor;
import com.WebPortfolio.Reporitory.MessageRepository;
import com.WebPortfolio.Reporitory.VisitorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	VisitorRepository visitorRepository;

	public Iterable<Message> getMessageListTop5ByOrderByIdDesc() {
		return messageRepository.findTop5ByOrderByIdDesc();

	}

	@Transactional
	public void saveMessage(Message message, HttpServletRequest req) {
		String reqIp = getIp(req);
		try {
			
			messageRepository.save(message);
			
			Visitor aVisitor = visitorRepository.findByIp(reqIp);
			
			message.setVisitor(aVisitor);
			
			log.info("Message Saved by " + reqIp);
			
		}catch(NonUniqueResultException ex) {
			log.error("IP:" + reqIp + "multiple selected row ");	
		}
	}

	private String getIp(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
