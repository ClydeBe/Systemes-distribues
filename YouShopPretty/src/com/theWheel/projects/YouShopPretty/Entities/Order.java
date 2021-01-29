package com.theWheel.projects.YouShopPretty.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order" , schema = "public")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	private Long Id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "is_processed")
	private boolean isProcessed;

	
	@Column(name = "products")
	private String products;
	
//	@Column(name = "order_price")
//	private Long orderPrice;
	
	public Order() {
		super();
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

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}
	
//	public Long getOrderPrice() {
//		return orderPrice;
//	}
//
//	public void setOrderPrice(Long orderPrice) {
//		this.orderPrice = orderPrice;
//	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
}
