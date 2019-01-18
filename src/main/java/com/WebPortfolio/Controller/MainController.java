package com.WebPortfolio.Controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.WebPortfolio.Model.Instagram;
import com.WebPortfolio.Model.Message;
import com.WebPortfolio.Model.Portfolio;
import com.WebPortfolio.Reporitory.MessageRepository;
import com.WebPortfolio.Reporitory.PortfolioImgReporitory;
import com.WebPortfolio.Reporitory.PortfolioReporitory;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	PortfolioReporitory portfolioReporitory;
	@Autowired
	PortfolioImgReporitory portfolioImgReporitory;

	@Value("${upload.uploadingdir}")
	String uploadingdir;

	@Value("${upload.portfolioPath}")
	String portfolioPath;

	@Value("${aws.accessKey}")
	String accessKey;

	@Value("${aws.secretKey}")
	String secretKey;

	@Value("${instagram.accessToken}")
	String instagramToken;

	@RequestMapping("/")
	@Transactional
	public String home(Model model) throws JSONException {

		log.debug("home Test");

		Message ms = new Message();
		model.addAttribute("formModel", ms);

		Iterable<Message> list = messageRepository.findTop5ByOrderByIdDesc();
		model.addAttribute("datalist", list);

		List<Portfolio> list2 = portfolioReporitory.findAll();

		/*
		 * AwsS3 s3 = new AwsS3(accessKey,secretKey);
		 * s3.createBucket("jiyong-portfolio");
		 * 
		 * for (Portfolio portfolio : list2) {
		 * s3.getObject("jiyong-portfolio",portfolio.getId() + "/" +
		 * portfolio.getThumbnailPath(), portfolioPath + portfolio.getThumbnailPath());
		 * for (PortfolioImg portfolioimg : portfolio.getListPortfolioImg()) {
		 * s3.getObject("jiyong-portfolio", portfolio.getId() + "/" +
		 * portfolioimg.getFileName(), portfolioPath + portfolioimg.getFileName()); } }
		 */

		model.addAttribute("portfolioList", list2);
		model.addAttribute("portfolioPath", portfolioPath);

		RestTemplate restTemplate = new RestTemplate();

		URI uri = UriComponentsBuilder.fromHttpUrl("https://api.instagram.com/v1/users/self/media/recent/")
				.queryParam("access_token", instagramToken).queryParam("count", 12).build().toUri();

		String response = restTemplate.getForObject(uri, String.class);
		JSONObject json = new JSONObject(response);
		JSONArray dataArray = json.getJSONArray("data");
		
		List<Instagram> instagramList = new ArrayList<Instagram>();		
		
		for(int i=0;i < dataArray.length();i++){                                     
		    JSONObject obj = dataArray.getJSONObject(i);
		    Instagram tmpInstagram = new Instagram();
		    tmpInstagram.setLink(obj.getString("link"));
		
		    tmpInstagram.setImgPath(obj.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
		    tmpInstagram.setLike(obj.getJSONObject("likes").getInt("count"));
		    tmpInstagram.setComment(obj.getJSONObject("comments").getInt("count"));
		    instagramList.add(tmpInstagram);
		    
		    
		}
					
		
		model.addAttribute("instagramList",instagramList);

		return "index";
	}

	@RequestMapping("/index")
	public String index2(Model model) {
		log.debug("index page from redirect");

		Iterable<Message> list = messageRepository.findTop5ByOrderByIdDesc();
		model.addAttribute("datalist", list);

		return "index";
	}
}
