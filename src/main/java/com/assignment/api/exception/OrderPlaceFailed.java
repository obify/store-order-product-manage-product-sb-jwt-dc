package com.assignment.api.exception;

public class OrderPlaceFailed extends RuntimeException{

	public OrderPlaceFailed(String s){
		super(s);
	}
}
