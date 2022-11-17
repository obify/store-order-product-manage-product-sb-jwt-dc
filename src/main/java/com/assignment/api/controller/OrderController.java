package com.assignment.api.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.api.constants.CodeMessage;
import com.assignment.api.model.APIResponse;
import com.assignment.api.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    ServletContext context;

    @Autowired
    OrderService orderService;


    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/")
    public ResponseEntity<?> allOrders() {
    	APIResponse response = new APIResponse();
    	response.setCode(CodeMessage.SUCCESS_DATAFETCH);
    	response.setMessage(CodeMessage.SUCCESS_DATAFETCH_MESSAGE);
    	response.setPayload(orderService.listAllNewOrders());
        return new ResponseEntity(response, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(int[] cartIds){
    	APIResponse response = new APIResponse();
    	response.setCode(CodeMessage.SUCCESS_ORDER);
    	response.setMessage(CodeMessage.SUCCESS_ORDER_MESSAGE);
    	orderService.placeOrder(cartIds);
    	return new ResponseEntity(response, HttpStatus.OK);
    }
    
  
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/searchOrder/{orderId}")
    public ResponseEntity<?> searchOrderByOrderId(@PathVariable("orderId") String orderId) {
    	APIResponse response = new APIResponse();
    	response.setCode(CodeMessage.SUCCESS_DATAFETCH);
    	response.setMessage(CodeMessage.SUCCESS_DATAFETCH_MESSAGE);
    	response.setPayload(orderService.searchOrderByOrderId(orderId));
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
