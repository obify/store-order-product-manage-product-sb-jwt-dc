package com.assignment.api.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.api.entity.Order;
import com.assignment.api.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>  {
    List<Order> findByUserId(User user);
    List<Order> findByOrderId(String orderId);
    List<Order> findByOrderStatusIn(List<String> orderStatus);    
    List<Order> findLast5ByOrderStatus(String orderStatus);
    List<Order> findTop5ByCreatedAtOrderByQuantityDesc(Date todayDate);
    List<Order> findByCreatedAtBetween(Date fromDate, Date toDate);
    
    @Query("select o from Order o where month(o.createdAt) = ?1")
    List<Order> findAllByCreatedAt(Integer month);
}
