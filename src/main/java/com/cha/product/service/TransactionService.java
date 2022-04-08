package com.cha.product.service;

import java.util.List;

import com.cha.product.model.Transaction;

public interface TransactionService {

	Transaction saveTransaction(Transaction transaction);

	Transaction updateTransaction(Transaction transaction);

	List<Transaction> findAllTransaction();

	Long nbOfTransactions();

}
