package com.WebPortfolio.Reporitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WebPortfolio.Model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {


	
	List<Message> findTop5ByOrderByIdDesc();
	
	
}
