package com.WebPortfolio.Model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name="visitor")
@Data
public class Visitor {
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;	
	
	@Column
	private String ip;	
	
	@Column
	private int count;	
	
	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	private Timestamp createDate;	
	
	@Column(name = "UPDATE_DATE", insertable = false)
	@UpdateTimestamp
	private Timestamp updateDate;


}
