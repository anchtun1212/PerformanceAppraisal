package com.cha.performanceappraisal.service;

import java.util.List;

import com.cha.performanceappraisal.model.Product;

public interface ProductService {

	Product saveProduct(Product product);

	Product updateProduct(Product product);

	void deleteProductById(Long id);

	List<Product> findAllProducts();

	Long nbOfProduct();

}
