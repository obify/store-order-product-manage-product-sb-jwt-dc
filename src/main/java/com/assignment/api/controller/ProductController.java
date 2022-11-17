package com.assignment.api.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.api.constants.CodeMessage;
import com.assignment.api.model.APIResponse;
import com.assignment.api.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ServletContext context;

	@Autowired
	ProductService productService;


	@PreAuthorize("hasRole('USER')")
	@GetMapping("/")
	public ResponseEntity<?> getProducts() {
		APIResponse response = new APIResponse();
		response.setCode(CodeMessage.SUCCESS_DATAFETCH);
		response.setMessage(CodeMessage.SUCCESS_DATAFETCH_MESSAGE);
		response.setPayload(productService.listAllProduct());

		return new ResponseEntity(response, HttpStatus.OK);
	}
}
