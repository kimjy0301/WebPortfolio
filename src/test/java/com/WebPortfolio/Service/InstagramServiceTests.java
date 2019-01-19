package com.WebPortfolio.Service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import com.WebPortfolio.Model.Instagram;

@RunWith(MockitoJUnitRunner.class)
public class InstagramServiceTests {

	@Mock
	InstagramService instagramService;

	@Value("${instagram.accessToken}")
	String instagramToken;

	List<Instagram> results;

	@Before
	public void setUp() throws Exception {
		List<Instagram> tmpResults = new ArrayList<Instagram>();
		
		for(int i=0;i < 100; i++) {
			
			Instagram tmpInstagram = new Instagram();
			tmpInstagram.setImgPath("testPath" + String.valueOf(i));
			tmpInstagram.setLike(i);		
			tmpResults.add(tmpInstagram);
			
			
		}
		
		

		when(instagramService.getIntagramList()).thenReturn(tmpResults);

	}

	@Test
	public void getIntagramList() throws Exception {

		results=instagramService.getIntagramList();
		assertEquals(100, results.size());
		;
	}

}
