package com.WebPortfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = "com.WebPortfolio")
@PropertySource("upload.properties")
public class WebPortfolioApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebPortfolioApplication.class, args);		
	}
}