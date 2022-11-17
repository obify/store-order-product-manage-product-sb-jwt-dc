package com.assignment.api.model;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class OrderDTO {



	private String orderId;

	private String orderStatus;

	private Float orderamount;

    private Date orderDate;
    
    private String customerName;
    
    private String customerMobileNo;
    
    private String customerEmail;
    
    private List<ProductListDTO> productList = new ArrayList<>();
	
	public OrderDTO() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}


	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getOrderStatus() {
		return orderStatus;
	}


	
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Float getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(Float orderamount) {
		this.orderamount = orderamount;
	}


	public Date getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public String getCustomerMobileNo() {
		return customerMobileNo;
	}



	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}



	public String getCustomerEmail() {
		return customerEmail;
	}



	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}



	public List<ProductListDTO> getProductList() {
		return productList;
	}



	public void setProductList(List<ProductListDTO> productList) {
		this.productList = productList;
	}

}
