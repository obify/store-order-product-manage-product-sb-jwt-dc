package com.assignment.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.assignment.api.constants.CodeMessage;
import com.assignment.api.entity.Cart;
import com.assignment.api.entity.Product;
import com.assignment.api.entity.User;
import com.assignment.api.exception.NotFoundException;
import com.assignment.api.model.CartDTO;
import com.assignment.api.model.CartListDTO;
import com.assignment.api.repository.CartRepository;
import com.assignment.api.repository.ProductRepository;
import com.assignment.api.repository.UserRepository;

@Service
public class CartproductService {

  @Autowired
  private CartRepository cartproductRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  ProductService productService;


  public List<Cart> listAllCartproduct() {
    return cartproductRepository.findAll();
  }

  public List<CartListDTO> saveCartproduct(CartDTO cart) {
    Cart scart = new Cart();
    User user = null;
    if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
      String userName = SecurityContextHolder.getContext().getAuthentication().getName();
      user = userRepository.findByUserName(userName);
    }
    Product product = productRepository.findById(cart.getProductId()).get();
    BeanUtils.copyProperties(cart, scart);
    scart.setProductId(product);
    scart.setUserId(user);
    cartproductRepository.save(scart);
    return getUserCart();
  }


  public List<CartListDTO> getUserCart(){
    User user = null;
    if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
      String userName = SecurityContextHolder.getContext().getAuthentication().getName();
      user = userRepository.findByUserName(userName);
    }
    List<Cart> carts = cartproductRepository.findByUserId(user);
    if(!(carts.size()>0)) {
      throw new NotFoundException(CodeMessage.EMPTY_CART_MESSAGE);
    }
    return cartProduct(carts);
  }


  private List<CartListDTO> cartProduct(List<Cart> carts){
    List<CartListDTO> cartLists = new ArrayList<>();
    for(Cart cart : carts) {
      CartListDTO cartList = new CartListDTO();
      Product product = cart.getProductId();

      cartList.setCartID(cart.getId());
      cartList.setProductName(product.getProductname());
      cartList.setProductPrize(product.getProductPrice().getPrice());
      cartList.setQuantity(String.valueOf(cart.getCount()));

      cartLists.add(cartList);
    }

    return cartLists;
  }
}
