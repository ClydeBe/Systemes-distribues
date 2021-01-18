package com.theWheel.projects.YouShopPretty.Entities;

import javax.json.Json;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "whishlist", schema = "public")
public class Whishlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_whishlist")
	public Long Id;
	
	@Column(name = "user_id")
	public Long userId;
	
	@Column(name = "products")
	public Json products;

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
	
	

}
