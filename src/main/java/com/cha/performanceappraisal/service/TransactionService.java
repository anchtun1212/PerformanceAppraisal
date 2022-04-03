package com.cha.performanceappraisal.service;

import java.util.List;

import com.cha.performanceappraisal.model.Transaction;

public interface TransactionService {

	Transaction saveTransaction(Transaction transaction);

	Transaction updateTransaction(Transaction transaction);

	List<Transaction> findAllTransaction();

	Long nbOfTransactions();

}
