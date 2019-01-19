package com.WebPortfolio.Thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class ThymeleafDialectBean {
	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

	/*
	 * @Configuration public class WebConfig implements WebMvcConfigurer {
	 * 
	 * @Override public void addViewControllers(ViewControllerRegistry registry) {
	 * 
	 * registry.addViewController("/").setViewName("foward:/index");
	 * 
	 * } }
	 * 
	 */

	/*
	 * @Bean public ServletWebServerFactory servletContainer() {
	 * TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
	 * 
	 * @Override protected void postProcessContext(Context context) {
	 * SecurityConstraint securityConstraint = new SecurityConstraint();
	 * securityConstraint.setUserConstraint("CONFIDENTIAL"); SecurityCollection
	 * collection = new SecurityCollection(); collection.addPattern("/*");
	 * securityConstraint.addCollection(collection);
	 * context.addConstraint(securityConstraint); } };
	 * tomcat.addAdditionalTomcatConnectors(redirectConnector()); return tomcat; }
	 * 
	 * private Connector redirectConnector() { Connector connector = new
	 * Connector("org.apache.coyote.http11.Http11NioProtocol");
	 * connector.setScheme("http"); connector.setPort(8080);
	 * connector.setSecure(false); connector.setRedirectPort(8443); return
	 * connector; }
	 */
}

