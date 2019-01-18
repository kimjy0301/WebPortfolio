package com.WebPortfolio.Service;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WebPortfolio.Model.Visitor;
import com.WebPortfolio.Reporitory.VisitorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VisitorService {
	@Autowired
	VisitorRepository visitorRepository;

	@Transactional
	public void saveVisitor(HttpServletRequest request) {

		String reqIp = getIp(request);
		Visitor searchVisitor;
		try {
			 searchVisitor = visitorRepository.findByIp(reqIp);
			 
				if (searchVisitor != null) {
					searchVisitor.setCount(searchVisitor.getCount() + 1);
					visitorRepository.save(searchVisitor);
					log.info("IP : " + reqIp + " / Visit Count : " +  String.valueOf(searchVisitor.getCount()));
				} else {
					Visitor newVisitor = new Visitor();
					newVisitor.setIp(reqIp);
					visitorRepository.save(newVisitor);
					log.info("IP:" + reqIp + " visit first time");			
				}
		}catch(NonUniqueResultException ex) {
			log.error("IP:" + reqIp + "multiple selected row ");	
		}
	}

	private String getIp(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
