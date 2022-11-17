package com.assignment.api.model.builder;

import com.assignment.api.entity.Product;
import com.assignment.api.entity.Productprice;

public class ProductBuilder {

  private final Product product;
  Productprice productprice = new Productprice();

  private ProductBuilder() {
    product = new Product();
  }
  /**
   * Creates a builder.
   *
   * @return the builder.
   */
  public static ProductBuilder getInstance() {
    return new ProductBuilder();
  }

  public ProductBuilder addNewProduct() {

    product.setId(1);
    productprice.setProductId(product);
    productprice.setQuantity("12");
    productprice.setId(1);
    product.setProductname("Test");
    product.setProductPrice(productprice);
    product.setDescription("Testing");

    return this;
  }

  public Product build() {
    return product;
  }

}
