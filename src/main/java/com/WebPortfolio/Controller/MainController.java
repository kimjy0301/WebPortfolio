package com.WebPortfolio.Controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.WebPortfolio.Service.InstagramService;
import com.WebPortfolio.Service.MessageService;
import com.WebPortfolio.Service.PortfolioService;
import com.WebPortfolio.Service.VisitorService;
import com.WebPortfolio.WebSocket.ChatService;

@Controller
public class MainController {

	@Autowired
	InstagramService instagramService;

	@Autowired
	PortfolioService portfolioService;

	@Autowired
	MessageService messageService;

	@Autowired
	VisitorService visitorService;
	@Autowired
	ChatService chatService;

	@Value("${upload.portfolioPath}")
	String portfolioPath;

	@RequestMapping("/")
	public ModelAndView home(ModelAndView mav, HttpServletRequest request) throws JSONException {

		visitorService.saveVisitor(request);

		mav.setViewName("index");

		mav.addObject("datalist", messageService.getMessageListTop5ByOrderByIdDesc());
		mav.addObject("portfolioList", portfolioService.getPortfolioList());
		mav.addObject("portfolioPath", portfolioPath);
		mav.addObject("instagramList", instagramService.getIntagramList());

		return mav;
	}

	@RequestMapping("/index")
	public String index2(Model model) {
		return "index";
	}

	@RequestMapping("/client")
	public String client(Model model) {
		return "chatclient";
	}

	@RequestMapping("/server")
	public String server(Model model) {
		

		HashMap<String,String> returnMap = new HashMap<String,String>();
		HashMap<String,String> list = chatService.getChat();
		for (String key : list.keySet()) {
			
			String val = list.get(key);
			if (!val.equals("")) {
				returnMap.put(key, val);				
			}
		}		
		model.addAttribute("userMap",returnMap);			
		
		
		return "chatserver";
	}

}
