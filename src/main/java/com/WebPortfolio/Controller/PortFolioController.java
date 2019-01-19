package com.WebPortfolio.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.WebPortfolio.Model.Portfolio;
import com.WebPortfolio.Service.PortfolioService;

@Controller
@RequestMapping("/portfolio")
public class PortFolioController {
	
	@Autowired
	PortfolioService portfolioService;
	

	
	@RequestMapping("/")
	public String uploading(Model model) {	
		model.addAttribute("list",portfolioService.getPortfolioList());		
		return "upload";		
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String uploadingPost(Portfolio portfolio, @RequestParam("input-b3[]") MultipartFile[] uploadingFiles,
			HttpServletRequest req, Model model) throws Exception {


		portfolioService.uploadPortfolio(portfolio,uploadingFiles);
		
				
		return "redirect:/portfolio/";
	}

}
