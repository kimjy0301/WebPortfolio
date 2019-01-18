package com.WebPortfolio.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name="portfolioimg")
@Data
public class PortfolioImg {
	
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;	
	
	@Column
	private String fileName;	
	
	@ManyToOne
	@JoinColumn(name="portfolioImgId")
	private Portfolio portfolio;
	
	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	private Timestamp createDate;	
	
	@Column(name = "UPDATE_DATE", insertable = false)
	@UpdateTimestamp
	private Timestamp updateDate;

	

}
