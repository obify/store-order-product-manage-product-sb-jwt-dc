package com.assignment.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tblproductprice")
public class Productprice extends BaseEntity<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product productId;

	@Column(name = "quantity")
	private String quantity;


	@Column(name = "price")
	private float price;

	@Column(name = "inStock")
	private boolean inStock;

	public Productprice() {
		super();
	}

	public Productprice(Product productId, String quantity, float price) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public void setProductId(Product productId) {
		this.productId = productId;
	}

	@JsonIgnore
	public Product getProductId() {
		return productId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}



}
