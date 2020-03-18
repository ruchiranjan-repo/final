package com.hcl.bank.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.hcl.bank.entity.Account;
import com.hcl.bank.entity.Customer;

@DataJpaTest
public class AccountRepositoryTest {

	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	AccountRepository accountRepository;

	private static final Long ACCOUNT_NUMBER = 1000L;
	private static final String ACCOUNT_TYPE = "SAVINGS";
	private static final String IFSC_CODE = "IFSC001";
	private static final Long ACCOUNT_NUMBER_NOTFOUND = 10000L;

	Account account;
	Customer customer;

	@BeforeEach
	public void setUp() {
		account = new Account();
		account.setAccountNumber(ACCOUNT_NUMBER);
		account.setAccountType(ACCOUNT_TYPE);
		account.setAvailableBalance(new Double(10000));
		account.setIfscCode(IFSC_CODE);
		customer = new Customer();
		customer.setCustomerName("TEST_CUSTOMER");
		account.setCustomer(customer);
	}

	@Test
	public void testFindByAccountNumber() {

		testEntityManager.persistAndFlush(account);

		Optional<Account> retrievedAccount = accountRepository.findByAccountNumber(ACCOUNT_NUMBER);

		assertThat(retrievedAccount.get().getAccountNumber()).isEqualTo(ACCOUNT_NUMBER);
		assertThat(retrievedAccount.get().getAccountType()).isEqualTo(ACCOUNT_TYPE);
		assertThat(retrievedAccount.get().getAvailableBalance()).isEqualTo(new Double(10000));
		assertThat(retrievedAccount.get().getAvailableBalance()).isEqualTo(new Double(10000));
		assertThat(retrievedAccount.get().getCustomer().getCustomerName()).isEqualTo("TEST_CUSTOMER");
	}

	@Test
	public void testFindByAccountNumberNotFound() {

		testEntityManager.persistAndFlush(account);

		Optional<Account> retrievedAccount = accountRepository.findByAccountNumber(ACCOUNT_NUMBER_NOTFOUND);

		assertThat(retrievedAccount.isPresent()).isEqualTo(false);

	}
}
