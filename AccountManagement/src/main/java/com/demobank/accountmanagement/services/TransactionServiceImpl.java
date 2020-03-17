package com.demobank.accountmanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.demobank.accountmanagement.models.Transaction;
import com.demobank.accountmanagement.repositories.TransactionRepository;

public class TransactionServiceImpl implements TransactionService{
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public List<Transaction> getAllTransaction(Long accountId) {
		List<Transaction>transactions= transactionRepository.findByAccountAccountId(accountId);
		return transactions;
	}

	

}
