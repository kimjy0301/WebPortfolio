package com.WebPortfolio.Service;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WebPortfolio.Bean.IpAdressControl;
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

	@Autowired
	IpAdressControl ipAdressControl;
	
	
	public Iterable<Message> getMessageListTop5ByOrderByIdDesc() {
		return messageRepository.findTop5ByOrderByIdDesc();

	}

	@Transactional
	public void saveMessage(Message message, HttpServletRequest req) {
		String reqIp = ipAdressControl.getIp(req);
		try {
			
			messageRepository.save(message);
			
			Visitor aVisitor = visitorRepository.findByIp(reqIp);
			
			message.setVisitor(aVisitor);

			log.info("IP : " + reqIp + " Message Save Success..");	
			
		}catch(NonUniqueResultException ex) {
			log.error("IP : " + reqIp + "multiple selected row ");	
		}
	}



}
