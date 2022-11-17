package com.assignment.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.assignment.api.entity.Product;
import com.assignment.api.model.ProductListDTO;
import com.assignment.api.model.builder.ProductBuilder;
import com.assignment.api.repository.ProductPrizeRepository;
import com.assignment.api.repository.ProductRepository;
import com.assignment.api.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.context.WebApplicationContext;

@RunWith(MockitoJUnitRunner.class)
@SpringJUnitConfig(AssignmentApplicationTest.TestConfig.class)
@SpringBootTest
class AssignmentApplicationTest {

  @Autowired
  private ProductRepository productRepos;

  @Autowired
  ProductService productService;

  @Configuration
  static class TestConfig {

    @MockBean
    ProductRepository productRepos;

    @MockBean
    ProductPrizeRepository productPrizeRepository;

    @Bean
    ProductService productService() {
      return new ProductService();
    }

  }


  @Test
  public void given_One_Product_InDatabase_When_FindAllProducts_Called_Then_ResultIsOneRecord() throws Exception {

    Product product = ProductBuilder.getInstance().addNewProduct().build();
    List<Product> productList = new ArrayList<>();
    productList.add(product);

    Mockito.when(productRepos.findAll()).thenReturn(productList);

    List<ProductListDTO> productListDTOS = productService.listAllProduct();

    verify(productRepos, times(1)).findAll();
    assertEquals(1, productListDTOS.size());

  }

}