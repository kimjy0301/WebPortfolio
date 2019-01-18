package com.WebPortfolio.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.WebPortfolio.Model.Message;
import com.WebPortfolio.Reporitory.MessageRepository;

@RestController
public class AjaxController {
	
	@Autowired
	MessageRepository messageRepository;
	
	@RequestMapping(value = "insertajax", method=RequestMethod.POST,produces = "application/json; charset=utf8")
	public Map<String, String> insertMessageAjax(@RequestBody Message message, HttpServletRequest req) throws Exception {
		
		Map<String, String> a = new  HashMap<String, String>();	

		if(message.getUserName() == "") {
			a.put("resultMessage", "성함을 입력해주세요.");
		}else if(message.getUserMail() == "") {
			a.put("resultMessage", "이메일을 입력해주세요.");
		}else if(message.getUserText() == "") {
			a.put("resultMessage", "하실말씀을 입력해주세요.");
		}else {
			messageRepository.saveAndFlush(message);
			a.put("resultMessage", "success");			

			a.put("name", message.getUserName());
			a.put("email", message.getUserMail());
			a.put("text", message.getUserText());
			
		}
		

		return a;
	}

}
