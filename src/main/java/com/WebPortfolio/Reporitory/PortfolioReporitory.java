package com.WebPortfolio.Reporitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WebPortfolio.Model.Portfolio;

@Repository
public interface PortfolioReporitory extends JpaRepository<Portfolio, Long> {

}
