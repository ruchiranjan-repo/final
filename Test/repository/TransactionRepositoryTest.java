package com.hcl.bank.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.CollectionUtils;

import com.hcl.bank.entity.Account;
import com.hcl.bank.entity.Transaction;

@DataJpaTest
public class TransactionRepositoryTest {
	private static final Long ACCOUNT_NUMBER = 1000L;
	private static final String ACCOUNT_TYPE = "SAVINGS";
	private static final String IFSC_CODE = "IFSC001";
	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
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
	public void testFindByAccounts() {
		Transaction transaction = testEntityManager.persist(t);
		testEntityManager.persist(t1);

		List<Transaction> transactions = transactionRepository.findByAccounts(transaction.getAccounts().getAccountId());
		assertFalse(CollectionUtils.isEmpty(transactions));

		assertThat(transactions.size()).isEqualTo(2);

	}

	@Test
	public void testFindByAccountsNotFound() {
		testEntityManager.persist(t);
		testEntityManager.persist(t1);

		List<Transaction> transactions = transactionRepository.findByAccounts(50L);
		assertTrue(CollectionUtils.isEmpty(transactions));
	}
}
