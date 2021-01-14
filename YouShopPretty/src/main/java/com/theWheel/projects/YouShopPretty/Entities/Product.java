package main.java.com.theWheel.projects.YouShopPretty.Entities;

import java.io.Serializable;

import javax.json.Json;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products", schema = "public")
public class Product implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_product")
	Long Id;

	Double price;
	Long quantity;
	String description;
	String name;
	String tags;
	Json caracteristics;
	Json imageLink;
	String category;

	public Product() {
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Json getCaracteristics() {
		return caracteristics;
	}

	public void setCaracteristics(Json caracteristics) {
		this.caracteristics = caracteristics;
	}

	public Json getImageLink() {
		return imageLink;
	}

	public void setImageLink(Json imageLink) {
		this.imageLink = imageLink;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
