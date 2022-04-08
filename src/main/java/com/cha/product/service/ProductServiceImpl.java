package com.cha.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cha.product.model.Product;
import com.cha.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Long nbOfProduct() {
		return productRepository.count();
	}

}
