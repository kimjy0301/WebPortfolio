package com.WebPortfolio.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WebPortfolio.Model.Message;
import com.WebPortfolio.Reporitory.MessageRepository;

@Service
public class MessageService {
	
	
	@Autowired
	MessageRepository messageRepository;

	
	public Iterable<Message> getMessageListTop5ByOrderByIdDesc(){		
		return  messageRepository.findTop5ByOrderByIdDesc();
		
	}
}
