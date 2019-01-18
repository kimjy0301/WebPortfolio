package com.WebPortfolio.Bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

	@Value("${upload.path}") 
	String uploadPath; 
	  
	
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	  
	String url = "file:///" + uploadPath;
	  
    registry.addResourceHandler("/upload/**").addResourceLocations(url);
    
  }
}