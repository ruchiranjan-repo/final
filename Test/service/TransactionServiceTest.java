package com.hcl.bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.CollectionUtils;

import com.hcl.bank.entity.Account;
import com.hcl.bank.entity.Transaction;
import com.hcl.bank.exception.LoginCustomerFoundException;
import com.hcl.bank.exception.TransactionNotFoundException;
import com.hcl.bank.repository.TransactionRepository;
import com.hcl.bank.response.TransactionResponse;

@SpringBootTest
public class TransactionServiceTest {
	private static final Long ACCOUNT_NUMBER = 1L;
	private static final Long ACCOUNT_NUMBER_2 = 2L;
	private static final String ACCOUNT_TYPE = "SAVINGS";
	private static final String IFSC_CODE = "IFSC001";
	@Autowired
	TransactionService transactionService;

	@MockBean
	TransactionRepository transactionRepository;

	Account account;
	Transaction t, t1;

	@BeforeEach
	public void setup() {
		account = new Account();
		account.setAccountNumber(ACCOUNT_NUMBER);
		account.setAccountType(ACCOUNT_TYPE);
		account.setAvailableBalance(new Double(10000));
		account.setIfscCode(IFSC_CODE);

		t = new Transaction();
		t.setAmount(new Double(100));
		t.setTransactionDateTime(new Date());
		t.setTransactionDescription("TransactionDescription");
		t.setTransactionStatus("SUCCESS");
		t.setTransactionType("DEBIT");
		t.setAccounts(account);

		t1 = new Transaction();
		t1.setAmount(new Double(1000));
		t1.setTransactionDateTime(new Date());
		t1.setTransactionDescription("TransactionDescription1");
		t1.setTransactionStatus("SUCCESS");
		t1.setTransactionType("CREDIT");
		t1.setAccounts(account);
	}

	@Test
	public void testGetTransactionsStatementByAccount() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(t);
		transactions.add(t1);
		when(transactionRepository.findByAccounts(ACCOUNT_NUMBER)).thenReturn(transactions);

		TransactionResponse response = transactionService.getTransactionsStatement(ACCOUNT_NUMBER);

		assertNotNull(response);
		assertEquals(response.getFundTransfer().size(), 2);
	}

	@Test
	public void testGetTransactionsStatementByAccountNotFound() {

		when(transactionRepository.findByAccounts(ACCOUNT_NUMBER_2)).thenReturn(new ArrayList<Transaction>());
		Assertions.assertThrows(TransactionNotFoundException.class, () -> {
			transactionService.getTransactionsStatement(ACCOUNT_NUMBER);
		});

	}

}
