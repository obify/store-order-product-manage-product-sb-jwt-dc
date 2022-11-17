package com.assignment.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.api.entity.Product;
import com.assignment.api.entity.Productprice;
import com.assignment.api.model.ProductListDTO;
import com.assignment.api.repository.ProductPrizeRepository;
import com.assignment.api.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductPrizeRepository productPrizeRepo;

	public List<ProductListDTO> listAllProduct() {
		List<Product> products = productRepository.findAll();
		return changeListObject(products);
	}

	private List<ProductListDTO> changeListObject(List<Product> products) {
		List<ProductListDTO> productList = new ArrayList<ProductListDTO>();
		for (Product pld : products) {
			ProductListDTO pd = new ProductListDTO();
			BeanUtils.copyProperties(pld, pd);
			pd.setQuantity(pld.getProductPrice().getQuantity());
			pd.setPrize(pld.getProductPrice().getPrice());

			productList.add(pd);
		}

		return productList;
	}

	public void updateQuantity(int productId, int quantity) {

		Product prd = productRepository.findById(productId).get();

		Productprice productprice = productPrizeRepo.findByProductId(prd);
		productprice.setQuantity(String.valueOf(Integer.parseInt(productprice.getQuantity())-quantity));

		if(Integer.parseInt(productprice.getQuantity()) < 10)
		{
			productprice.setInStock(false);
		}

		productPrizeRepo.save(productprice);
	}

}
