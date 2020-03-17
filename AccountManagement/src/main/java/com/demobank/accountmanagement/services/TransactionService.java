package com.demobank.accountmanagement.services;

import java.util.List;

import com.demobank.accountmanagement.models.Transaction;

public interface TransactionService {
	List<Transaction>getAllTransaction(Long accountId);

}
