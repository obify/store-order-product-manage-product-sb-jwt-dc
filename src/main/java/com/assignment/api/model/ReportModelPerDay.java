package com.assignment.api.model;

public class ReportModelPerDay {

	private String day;
	private String totalSale;
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(String totalSale) {
		this.totalSale = totalSale;
	}
	
	public ReportModelPerDay(String day, String totalSale) {
		super();
		this.day = day;
		this.totalSale = totalSale;
	}
	
	
}
