package com.WebPortfolio.Bean;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class HttpSessionConfig {
	@Bean // bean for http session listener
	public HttpSessionListener httpSessionListener() {
		return new HttpSessionListener() {
			@Override
			public void sessionCreated(HttpSessionEvent se) { // This method will be called when session created			
				log.info("Session Created with session id:" + se.getSession().getId());
			}

			@Override
			public void sessionDestroyed(HttpSessionEvent se) { // This method will be automatically called when session
																// destroyed
				log.info("Session Destroyed, Session id:" + se.getSession().getId());
			}
		};
	}

	@Bean // bean for http session attribute listener
	public HttpSessionAttributeListener httpSessionAttributeListener() {
		return new HttpSessionAttributeListener() {
			@Override
			public void attributeAdded(HttpSessionBindingEvent se) { // This method will be automatically called when
																		// session attribute added
				log.info("Attribute Added following information");
				log.info("Attribute Name:" + se.getName());
				log.info("Attribute Value:" + se.getValue());
			}

			@Override
			public void attributeRemoved(HttpSessionBindingEvent se) { // This method will be automatically called when
																		// session attribute removed
				log.info("Attribute Removed");
			}

			@Override
			public void attributeReplaced(HttpSessionBindingEvent se) { // This method will be automatically called when
																		// session attribute replace
				log.info("Attribute Replaced following information");
				log.info("Attribute Name:" + se.getName());
				log.info("Attribute Old Value:" + se.getValue());
			}
		};
	}
}