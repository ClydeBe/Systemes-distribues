package com.theWheel.projects.YouShopPretty.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment", schema = "public")
public class Comment {
	
	@Id
	@GeneratedValue
	@Column(name = "id_comment")
	private Long Id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "product_id")
	private Long productId;
	
	private String comment;

	public Comment() {
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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
