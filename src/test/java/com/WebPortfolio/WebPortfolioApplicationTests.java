package com.WebPortfolio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@TestPropertySource("classpath:upload.properties")
@ComponentScan(basePackages = "com.WebPortfolio")
@SpringBootTest
public class WebPortfolioApplicationTests {

	@Test
	public void contextLoads() {
		
				
		
	}

}

