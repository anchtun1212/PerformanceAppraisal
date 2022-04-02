package com.cha.performanceappraisal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cha.performanceappraisal.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
