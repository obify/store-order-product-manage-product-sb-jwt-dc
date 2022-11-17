package com.assignment.api.model;

public class ReportModel {

	private String productname;
	private int quantity;
	private String productPrize;
	private boolean inStock;

	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getProductPrize() {
		return productPrize;
	}
	public void setProductPrize(String productPrize) {
		this.productPrize = productPrize;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public ReportModel(String productname, int quantity) {
		super();
		this.productname = productname;
		this.quantity = quantity;
		this.productPrize = "0";
	}
	public ReportModel() {
		super();
	}
	
	
}
