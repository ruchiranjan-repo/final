package com.hcl.bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hcl.bank.entity.Account;
import com.hcl.bank.entity.Benificiary;
import com.hcl.bank.entity.Customer;
import com.hcl.bank.exception.IncorrectEmailIdAndPasswordException;
import com.hcl.bank.exception.LoggedOutCustomerException;
import com.hcl.bank.exception.LoginCustomerFoundException;
import com.hcl.bank.exception.UserNotFoundException;
import com.hcl.bank.repository.CustomerRepository;

@SpringBootTest
public class CustomerServiceTest {

	private static final Long ACCOUNT_NUMBER = 1000L;
	private static final String ACCOUNT_TYPE = "SAVINGS";
	private static final String IFSC_CODE = "IFSC001";

	private static final String CUSTOMER_EMAIL = "customerTest@gmail.com";
	private static final String CUSTOMER_EMAIL_NOT_AVAILABLE = "customerTest1@gmail.com";
	private static final String CUSTOMER_PASSWORD = "password";
	@Autowired
	CustomerService customerService;

	@MockBean
	CustomerRepository customerRepository;

	Account account;

	Customer customer, customer1;
	Benificiary benificiary;

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
		customer.setCustomerEmailId(CUSTOMER_EMAIL);
		customer.setCustomerMobileNumber(1111111111L);
		customer.setIsLoggedIn(false);
		customer.setPassword(CUSTOMER_PASSWORD);
		customer.setAccounts(accounts);
		customer.setBenificiaries(benificiaries);

		customer1 = new Customer();
		customer1.setCustomerName("TEST_CUSTOMER");
		customer1.setCustomerAddress("CUSTOMER ADDRESS");
		customer1.setCustomerDOB(LocalDate.now());
		customer1.setCustomerEmailId(CUSTOMER_EMAIL);
		customer1.setCustomerMobileNumber(1111111111L);
		customer1.setIsLoggedIn(true);
		customer1.setPassword(CUSTOMER_PASSWORD);
		customer1.setAccounts(accounts);
		customer1.setBenificiaries(benificiaries);

	}

	@Test
	public void testCustomerLogin() {
		when(customerRepository.findByCustomerEmailIdAndPassword(CUSTOMER_EMAIL, CUSTOMER_PASSWORD))
				.thenReturn(Optional.of(customer));
		when(customerRepository.save(customer)).thenReturn(customer1);

		String loginMessage = customerService.customerLogin(CUSTOMER_EMAIL, CUSTOMER_PASSWORD);

		assertNotNull(loginMessage);
		assertThat(loginMessage).isEqualTo("login successfull");

	}

	@Test
	public void testCustomerLoginWithIncorrectEmailOrPassword() {
		when(customerRepository.findByCustomerEmailIdAndPassword(CUSTOMER_EMAIL_NOT_AVAILABLE, CUSTOMER_PASSWORD))
				.thenReturn(Optional.empty());

		Assertions.assertThrows(IncorrectEmailIdAndPasswordException.class, () -> {
			customerService.customerLogin(CUSTOMER_EMAIL_NOT_AVAILABLE, CUSTOMER_PASSWORD);
		});

	}
	@Test
	public void testCustomerLoginWithAlreadyLoggedInUser() {
		when(customerRepository.findByCustomerEmailIdAndPassword(CUSTOMER_EMAIL, CUSTOMER_PASSWORD))
		.thenReturn(Optional.of(customer1));

		Assertions.assertThrows(LoginCustomerFoundException.class, () -> {
			customerService.customerLogin(CUSTOMER_EMAIL, CUSTOMER_PASSWORD);
		});

	}

	@Test
	public void testCheckLoggingStatus() {
		when(customerRepository.findByCustomerId(1L)).thenReturn(customer1);

		assertTrue(customerService.checkLoggingStatus(1L));
	}

	@Test
	public void testCheckLoggingStatusUserNotFoundException() {
		when(customerRepository.findByCustomerId(1L)).thenReturn(null);

		Assertions.assertThrows(UserNotFoundException.class, () -> {
			customerService.checkLoggingStatus(1L);
		});
	}

	
	
	@Test
	public void testCustomerLogout()
	{
		when(customerRepository.findByCustomerEmailId(CUSTOMER_EMAIL)).thenReturn(Optional.of(customer1));
		
		String msg=customerService.customerLogout(CUSTOMER_EMAIL);
		assertThat(msg).isEqualTo("Logout sccessful");
	}
	
	
	@Test
	public void testCustomerLogoutCustomerNotFound()
	{
		when(customerRepository.findByCustomerEmailId(CUSTOMER_EMAIL)).thenReturn(Optional.empty());
		
		
		Assertions.assertThrows(UserNotFoundException.class, () -> {
			customerService.customerLogout(CUSTOMER_EMAIL);
		});
	}
	
	@Test
	public void testCustomerLogoutForCustomerAlreadyLoggedout()
	{
		when(customerRepository.findByCustomerEmailId(CUSTOMER_EMAIL)).thenReturn(Optional.of(customer));
		
		
		Assertions.assertThrows(LoggedOutCustomerException.class, () -> {
			customerService.customerLogout(CUSTOMER_EMAIL);
		});
	}
}
