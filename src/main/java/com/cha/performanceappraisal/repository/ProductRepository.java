package com.cha.performanceappraisal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cha.performanceappraisal.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
