package com.theWheel.projects.YouShopPretty.Entities;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bill", schema = "public")
public class Bill implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_bill")
	Long Id;
	
	@Column(name = "user_id")
	Long userId;
	
	Json products;
	Date date;

	public Bill() {
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Json getProducts() {
		return products;
	}

	public void setProducts(Json products) {
		this.products = products;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@PostConstruct
	private void onCreate() {
		date = new Date();
	}
}
