package com.cha.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cha.product.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
