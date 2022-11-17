package com.assignment.api.repository;

import com.assignment.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.assignment.api.entity.Productprice;

@Repository
public interface ProductPrizeRepository extends JpaRepository<Productprice, Integer> {

	Productprice findByProductId(Product productId);
}
