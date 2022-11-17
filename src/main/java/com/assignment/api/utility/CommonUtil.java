package com.assignment.api.utility;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
	

	public CommonUtil() {

	}

	public static String getOrderIdBusiness(int id) {
		DecimalFormat myFormatter = new DecimalFormat("ORD000000");
		return myFormatter.format(id);
	}
}
