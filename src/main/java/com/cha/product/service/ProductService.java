package com.cha.product.service;

import java.util.List;

import com.cha.product.model.Product;

public interface ProductService {

	Product saveProduct(Product product);

	Product updateProduct(Product product);

	void deleteProductById(Long id);

	List<Product> findAllProducts();

	Long nbOfProduct();

}
