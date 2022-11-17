package com.assignment.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tblproduct")
public class Product extends BaseEntity<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "productname")
	private String productname;

	@Column(name= "description", length = 10000)
	private String description;


	@OneToOne(mappedBy = "productId", cascade = CascadeType.ALL )
	private Productprice productPrice;

	public Product() {
		super();
	}



	public Product(String productname, String description, Productprice productPrice) {
		super();
		this.productname = productname;
		this.description = description;
		this.productPrice = productPrice;

	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public Productprice getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Productprice productPrice) {
		this.productPrice = productPrice;
	}


}
