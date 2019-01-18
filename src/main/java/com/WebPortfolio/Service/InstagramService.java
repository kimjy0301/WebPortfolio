package com.WebPortfolio.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.WebPortfolio.Model.Instagram;


@Service
public class InstagramService {
	
	@Value("${instagram.accessToken}")
	String instagramToken;
		
	public List<Instagram> getIntagramList(){
		
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
		return instagramList;
	}
	
	

}
