package com.assignment.api.controller;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.api.constants.CodeMessage;
import com.assignment.api.model.APIResponse;
import com.assignment.api.model.CartDTO;
import com.assignment.api.service.CartproductService;


@RestController
@RequestMapping("/carts")
public class CartController {
  @Autowired
  ServletContext context;

  @Autowired
  CartproductService cartproductpriceService;

  @PreAuthorize("hasRole('USER')")
  @GetMapping("/userCart")
  public ResponseEntity<?> userCart(HttpServletRequest request){

    APIResponse response = new APIResponse();
    response.setPayload(cartproductpriceService.getUserCart());
    response.setCode(CodeMessage.SUCCESS_DATAFETCH);
    response.setMessage(CodeMessage.SUCCESS_DATAFETCH_MESSAGE);

    return new ResponseEntity(response, HttpStatus.OK);

  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/addToCart")
  public ResponseEntity<?> addToCart(@RequestBody CartDTO cart){

    APIResponse response = new APIResponse();
    response.setPayload(cartproductpriceService.saveCartproduct(cart));
    response.setCode(CodeMessage.ADD_CART);
    response.setMessage(CodeMessage.ADD_CART_MESSAGE);

    return new ResponseEntity(response, HttpStatus.OK);

  }
}
