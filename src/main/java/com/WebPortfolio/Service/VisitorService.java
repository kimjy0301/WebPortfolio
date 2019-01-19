package com.WebPortfolio.Service;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WebPortfolio.Bean.IpAdressControl;
import com.WebPortfolio.Model.Visitor;
import com.WebPortfolio.Reporitory.VisitorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VisitorService {
	@Autowired
	VisitorRepository visitorRepository;

	@Autowired
	IpAdressControl ipAdressControl;

	@Transactional
	public void saveVisitor(HttpServletRequest request) {

		Integer integer = (Integer) request.getSession().getAttribute("hitCounter"); // create session if not exists and
		// get attribute
		if (integer == null) {
			integer = 0;
			integer++;
			request.getSession().setAttribute("hitCounter", integer); // replace session attribute
		} else {
			integer++;
			request.getSession().setAttribute("hitCounter", integer); // replace session attribute
		}

		String reqIp = ipAdressControl.getIp(request);
		Visitor searchVisitor;
		try {
			searchVisitor = visitorRepository.findByIp(reqIp);

			if (searchVisitor != null) {
				searchVisitor.setCount(searchVisitor.getCount() + 1);
				visitorRepository.save(searchVisitor);
				log.info("IP : " + reqIp + " / Visit Count : " + String.valueOf(searchVisitor.getCount()));
			} else {
				Visitor newVisitor = new Visitor();
				newVisitor.setIp(reqIp);
				newVisitor.setCount(1);
				visitorRepository.save(newVisitor);
				log.info("IP : " + reqIp + " Visit first time");
			}
		} catch (NonUniqueResultException ex) {
			log.error("IP : " + reqIp + "multiple selected row ");
		}
	}

}
