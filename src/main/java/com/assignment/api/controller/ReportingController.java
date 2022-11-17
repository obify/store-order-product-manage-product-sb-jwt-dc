package com.assignment.api.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.api.constants.CodeMessage;
import com.assignment.api.model.APIResponse;
import com.assignment.api.model.MonthEnum;
import com.assignment.api.service.ReportingService;

@RestController
@RequestMapping("/reports")
public class ReportingController {

	@Autowired
	ReportingService reportService;

	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/topSellingProduct")
	public ResponseEntity<?> topSellingProduct(){
		APIResponse response = new APIResponse();
		response.setCode(CodeMessage.SUCCESS_DATAFETCH);
		response.setMessage(CodeMessage.SUCCESS_DATAFETCH_MESSAGE);
		response.setPayload(reportService.topSellingProduct());
		return new ResponseEntity(response, HttpStatus.OK);

	}

	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/totalSelling")
	public ResponseEntity<?> totalSelling(String fromDate, String toDate)
			throws ParseException {
		APIResponse response = new APIResponse();
		response.setCode(CodeMessage.SUCCESS_DATAFETCH);
		response.setMessage(CodeMessage.SUCCESS_DATAFETCH_MESSAGE);
		response.setPayload(reportService.sellingPerDayWithDateRange(fromDate, toDate));
		return new ResponseEntity(response, HttpStatus.OK);

	}

	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/sellingOfMonth")
	public ResponseEntity<?> sellingOfMonth(MonthEnum month){
		APIResponse response = new APIResponse();
		response.setCode(CodeMessage.SUCCESS_DATAFETCH);
		response.setMessage(CodeMessage.SUCCESS_DATAFETCH_MESSAGE);
		response.setPayload(reportService.sellingProductOfMonth(Integer.parseInt(month.getStr())));
		return new ResponseEntity(response, HttpStatus.OK);

	}
}
