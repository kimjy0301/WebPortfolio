package com.WebPortfolio.Controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.WebPortfolio.Service.InstagramService;
import com.WebPortfolio.Service.MessageService;
import com.WebPortfolio.Service.PortfolioService;

@Controller
public class MainController {

	@Autowired
	InstagramService instagramService;
	
	@Autowired
	PortfolioService portfolioService;
	
	@Autowired
	MessageService messageService;	
	
	@Value("${upload.portfolioPath}")
	String portfolioPath;


	@RequestMapping("/")
	public String home(Model model) throws JSONException {
		
		model.addAttribute("datalist", messageService.getMessageListTop5ByOrderByIdDesc());
		model.addAttribute("portfolioList", portfolioService.getPortfolioList());
		model.addAttribute("portfolioPath", portfolioPath);		
		model.addAttribute("instagramList",instagramService.getIntagramList());

		return "index";
	}

	@RequestMapping("/index")
	public String index2(Model model) {
		return "index";
	}
}
