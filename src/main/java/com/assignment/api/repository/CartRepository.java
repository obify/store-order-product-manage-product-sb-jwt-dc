package com.assignment.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.api.entity.Cart;
import com.assignment.api.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

	List<Cart> findByUserId(User user);
}
