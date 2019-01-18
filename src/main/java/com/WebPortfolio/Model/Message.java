package com.WebPortfolio.Model;

import java.sql.Timestamp;

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
@Table(name="message")
@Data
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column
	private long id;
	
	@Column(length = 20 , nullable = false)
	private String userName;	
	
	@Column(length = 50 , nullable = false)
	private String userMail;
	
	@Column(length = 5000 , nullable = false)
	private String userText;	
	
	@ManyToOne
	@JoinColumn(name="visitorId")
	private Visitor visitor;
	
	
	
	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	private Timestamp createDate;	
	
	@Column(name = "UPDATE_DATE", insertable = false)
	@UpdateTimestamp
	private Timestamp updateDate;

	
}
