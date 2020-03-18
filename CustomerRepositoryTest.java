package com.hcl.bank.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.hcl.bank.entity.Account;
import com.hcl.bank.entity.Benificiary;
import com.hcl.bank.entity.Customer;

@DataJpaTest
public class CustomerRepositoryTest {
	private static final Long ACCOUNT_NUMBER = 1000L;
	private static final String ACCOUNT_TYPE = "SAVINGS";
	private static final String IFSC_CODE = "IFSC001";
	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	CustomerRepository customerRepository;

	Account account;
	Customer customer;
	Benificiary benificiary;;

	@BeforeEach
	public void setUp() {
		account = new Account();
		account.setAccountNumber(ACCOUNT_NUMBER);
		account.setAccountType(ACCOUNT_TYPE);
		account.setAvailableBalance(new Double(10000));
		account.setIfscCode(IFSC_CODE);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(account);

		benificiary = new Benificiary();
		benificiary.setBenificiaryAccountNumber(100L);
		benificiary.setBenificiaryName("BENIFICIARY1");
		List<Benificiary> benificiaries = new ArrayList<Benificiary>();
		benificiaries.add(benificiary);

		customer = new Customer();
		customer.setCustomerName("TEST_CUSTOMER");
		customer.setCustomerAddress("CUSTOMER ADDRESS");
		customer.setCustomerDOB(LocalDate.now());
		customer.setCustomerEmailId("customerTest@gmail.com");
		customer.setCustomerMobileNumber(1111111111L);
		customer.setIsLoggedIn(false);
		customer.setPassword("password");
		customer.setAccounts(accounts);
		customer.setBenificiaries(benificiaries);
	}

	@Test
	public void testFindByCustomerEmailIdAndPassword() {
		Customer savedCustomer = testEntityManager.persist(customer);

		Optional<Customer> customer = customerRepository.findByCustomerEmailIdAndPassword("customerTest@gmail.com",
				"password");

		assertThat(customer.get().getCustomerId()).isEqualTo(savedCustomer.getCustomerId());
		assertThat(customer.get().getCustomerName()).isEqualTo(savedCustomer.getCustomerName());
		assertThat(customer.get().getPassword()).isEqualTo(savedCustomer.getPassword());
		assertThat(customer.get().getCustomerAddress()).isEqualTo(savedCustomer.getCustomerAddress());
		assertThat(customer.get().getCustomerDOB()).isEqualTo(savedCustomer.getCustomerDOB());
		assertThat(customer.get().getCustomerMobileNumber()).isEqualTo(savedCustomer.getCustomerMobileNumber());
		assertThat(customer.get().getAccounts().size()).isEqualTo(savedCustomer.getAccounts().size());
		assertThat(customer.get().getBenificiaries().size()).isEqualTo(savedCustomer.getBenificiaries().size());

	}

	@Test
	public void testFindByCustomerEmailIdAndPasswordNotFound() {
		testEntityManager.persist(customer);

		Optional<Customer> customer = customerRepository.findByCustomerEmailIdAndPassword("customer@gmail.com",
				"password");

		assertFalse(customer.isPresent());

	}
	
	@Test
	public void testFindByCustomerId() {
		Customer savedCustomer = testEntityManager.persist(customer);

		Customer customer = customerRepository.findByCustomerId(savedCustomer.getCustomerId());

		assertThat(customer.getCustomerId()).isEqualTo(savedCustomer.getCustomerId());
		assertThat(customer.getCustomerName()).isEqualTo(savedCustomer.getCustomerName());
		assertThat(customer.getPassword()).isEqualTo(savedCustomer.getPassword());
		assertThat(customer.getCustomerAddress()).isEqualTo(savedCustomer.getCustomerAddress());
		assertThat(customer.getCustomerDOB()).isEqualTo(savedCustomer.getCustomerDOB());
		assertThat(customer.getCustomerMobileNumber()).isEqualTo(savedCustomer.getCustomerMobileNumber());
		assertThat(customer.getAccounts().size()).isEqualTo(savedCustomer.getAccounts().size());
		assertThat(customer.getBenificiaries().size()).isEqualTo(savedCustomer.getBenificiaries().size());

	}

	@Test
	public void testFindByCustomerIdNotFound() {
		testEntityManager.persist(customer);

		Customer customer = customerRepository.findByCustomerId(100L);

		assertNull(customer);

	}

}
