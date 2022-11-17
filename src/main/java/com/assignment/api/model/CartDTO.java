package com.assignment.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CartDTO {

	private int id;

	private int productId;

	private int count;

	public int getId() {
		return id;
	}

	@JsonIgnore
	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
