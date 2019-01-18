package com.WebPortfolio.Reporitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WebPortfolio.Model.Visitor;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

	List<Visitor> findById(long id);	
	Visitor findByIp(String ip);
	
	

}
