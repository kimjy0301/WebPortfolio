package com.WebPortfolio.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "portfolio")
@Data
public class Portfolio {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column
	private long id;

	@Column
	private String mainTitle;

	@Column
	private String subTitle;

	@Column(length=1500)
	private String content;

	@Column
	private String thumbnailPath;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "portfolio")
	private Collection<PortfolioImg> listPortfolioImg = new ArrayList<PortfolioImg>();

	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	private Timestamp createDate;	
	
	@Column(name = "UPDATE_DATE", insertable = false)
	@UpdateTimestamp
	private Timestamp updateDate;

}
