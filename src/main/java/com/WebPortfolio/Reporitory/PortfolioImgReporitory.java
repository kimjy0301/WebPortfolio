package com.WebPortfolio.Reporitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WebPortfolio.Model.PortfolioImg;

@Repository
public interface PortfolioImgReporitory extends JpaRepository<PortfolioImg, Long>  {

}
