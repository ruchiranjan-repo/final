package com.hcl.bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.naming.InsufficientResourcesException;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hcl.bank.dto.FundTransferdto;
import com.hcl.bank.entity.Account;
import com.hcl.bank.entity.Benificiary;
import com.hcl.bank.entity.Customer;
import com.hcl.bank.entity.FundTransfer;
import com.hcl.bank.entity.Transaction;
import com.hcl.bank.exception.AccountNotFoundException;
import com.hcl.bank.exception.BenificiaryNotFoundException;
import com.hcl.bank.exception.CustomerNotLoggedInException;
import com.hcl.bank.repository.AccountRepository;
import com.hcl.bank.repository.BenificiaryRepository;
import com.hcl.bank.repository.FundTransferRepository;

@SpringBootTest
public class FundTransferServiceTest {
	@MockBean
	private AccountRepository accountRepository;
	@MockBean
	private FundTransferRepository fundTransferRepository;

	@MockBean
	private CustomerService customerService;
	@MockBean
	private BenificiaryRepository benificiaryRepository;
	
	@Autowired
	private FundService fundService;
	
	Customer customer;
	Account account1,account2;
	FundTransferdto fundTransferdto ;
	List<Transaction> fromtransactionList ,totransactionList;	
	Benificiary beneficiary;
	
	@BeforeEach
	public void setup()
	{ customer = new Customer();
	customer.setCustomerId(1L);
	customer.setCustomerName("TEST_CUSTOMER");
	customer.setCustomerAddress("CUSTOMER ADDRESS");
	customer.setCustomerDOB(LocalDate.now());
	customer.setCustomerEmailId("abc@gmail.com");
	customer.setCustomerMobileNumber(1111111111L);
	customer.setIsLoggedIn(true);
	customer.setPassword("abc");	
	beneficiary= new Benificiary();
	beneficiary.setBenificiaryId(1L);
	beneficiary.setBenificiaryAccountNumber(1234L);
	beneficiary.setBenificiaryName("vasu");
	beneficiary.setCustomer(customer);
		
	account1 = new Account();
	account1.setAccountId(1L);
	account1.setAccountNumber(123L);
	account1.setAvailableBalance(100000.0);
	account1.setIfscCode("123fs");
	account1.setCustomer(customer);
	 account2 = new Account();
	account2.setAccountId(2L);
	account2.setAccountNumber(1234L);
	account2.setAvailableBalance(20000.0);
	account2.setIfscCode("1234fs");
	account2.setCustomer(customer);
	 fundTransferdto = new FundTransferdto();
	fundTransferdto.setFromAccount(123L);
	fundTransferdto.setToAccount(1234L);
	fundTransferdto.setAmount(1000.0);
	fundTransferdto.setDescription("deposit");
	
	List<Transaction> fromtransactionList = new ArrayList<Transaction>();
	Transaction fromTransaction = new Transaction();
	fromTransaction.setAmount(fundTransferdto.getAmount());
	fromTransaction.setTransactionDateTime(new Date());
	fromTransaction.setTransactionStatus("success");
	fromTransaction.setTransactionDescription(fundTransferdto.getDescription());
	fromTransaction.setTransactionType("debited");
	fromtransactionList.add(fromTransaction);
	account1.setTransaction(fromtransactionList);
	fromTransaction.setAccounts(account1);
	
	List<Transaction> totransactionList = new ArrayList<Transaction>();
	Transaction toTransaction = new Transaction();
	toTransaction.setAmount(fundTransferdto.getAmount());
	toTransaction.setTransactionDateTime(new Date());
	toTransaction.setTransactionStatus("success");
	toTransaction.setTransactionDescription(fundTransferdto.getDescription());
	toTransaction.setTransactionType("credited");
	totransactionList.add(toTransaction);
	account2.setTransaction(totransactionList);
	toTransaction.setAccounts(account1);
	
	
		
	}

	@Test
	public void fundTransferTest() throws InsufficientResourcesException, AccountNotFoundException {
		
		Mockito.when( benificiaryRepository.findByUserIdAndAccountNumber(
               account1.getCustomer().getCustomerId(), fundTransferdto.getToAccount()))
		.thenReturn(Optional.of(beneficiary));
		Mockito.when(accountRepository.findByAccountNumber(fundTransferdto.getFromAccount()))
				.thenReturn(Optional.of(account1));
		Mockito.when(accountRepository.findByAccountNumber(fundTransferdto.getToAccount()))
				.thenReturn(Optional.of(account2));
		Mockito.when(customerService.checkLoggingStatus(account1.getCustomer().getCustomerId()))
				.thenReturn(account1.getCustomer().getIsLoggedIn());
	

		FundTransfer fundTransfer = new FundTransfer(account1.getAccountNumber(), fundTransferdto.getAmount(),
				LocalDateTime.now(), account2.getAccountNumber());
		Mockito.when(fundTransferRepository.save(fundTransfer)).thenReturn(fundTransfer);
		String response = fundService.fundTransfer(fundTransferdto);
		assertEquals("Transaction successful", response);

	}

	@Test
	public void TestInsufficientResourcesException() throws InsufficientResourcesException, AccountNotFoundException {
		account1.setAvailableBalance(100.0);
		
		Mockito.when(accountRepository.findByAccountNumber(fundTransferdto.getFromAccount()))
				.thenReturn(Optional.of(account1));
		Mockito.when(accountRepository.findByAccountNumber(fundTransferdto.getToAccount()))
				.thenReturn(Optional.of(account2));
		Mockito.when( benificiaryRepository.findByUserIdAndAccountNumber(
	               account1.getCustomer().getCustomerId(), fundTransferdto.getToAccount()))
			.thenReturn(Optional.of(beneficiary));
		
		FundTransfer fundTransfer = new FundTransfer(account1.getAccountNumber(), fundTransferdto.getAmount(),
				LocalDateTime.now(), account2.getAccountNumber());
		Mockito.when(fundTransferRepository.save(fundTransfer)).thenReturn(fundTransfer);
		Mockito.when(customerService.checkLoggingStatus(account1.getCustomer().getCustomerId()))
		.thenReturn(account1.getCustomer().getIsLoggedIn());
		
		assertThrows(InsufficientResourcesException.class, () -> {
			fundService.fundTransfer(fundTransferdto);
		});
	}
	
	@Test
	public void testUserNotLoggedInException() throws InsufficientResourcesException, AccountNotFoundException {
		customer.setIsLoggedIn(false);
		Mockito.when(accountRepository.findByAccountNumber(fundTransferdto.getFromAccount()))
				.thenReturn(Optional.of(account1));
		Mockito.when(accountRepository.findByAccountNumber(fundTransferdto.getToAccount()))
				.thenReturn(Optional.of(account2));

		Mockito.when( benificiaryRepository.findByUserIdAndAccountNumber(
	               account1.getCustomer().getCustomerId(), fundTransferdto.getToAccount()))
			.thenReturn(Optional.of(beneficiary));

		FundTransfer fundTransfer = new FundTransfer(account1.getAccountNumber(), fundTransferdto.getAmount(),
				LocalDateTime.now(), account2.getAccountNumber());
		Mockito.when(fundTransferRepository.save(fundTransfer)).thenReturn(fundTransfer);
		Mockito.when(customerService.checkLoggingStatus(account1.getCustomer().getCustomerId()))
		.thenReturn(account1.getCustomer().getIsLoggedIn());
		
		assertThrows(CustomerNotLoggedInException.class, () -> {
			fundService.fundTransfer(fundTransferdto);
		});
	}
	
	@Test
	public void TestAccountNotFoundException() throws InsufficientResourcesException, AccountNotFoundException {
		
		
		Mockito.when(accountRepository.findByAccountNumber(fundTransferdto.getFromAccount()))
				.thenReturn(Optional.empty());
		assertThrows(AccountNotFoundException.class, () -> {
			fundService.fundTransfer(fundTransferdto);
		});
	}
	
	@Test
	public void TestAccountNotFoundExceptionForToAccount() throws InsufficientResourcesException, AccountNotFoundException {
		
		Mockito.when(accountRepository.findByAccountNumber(fundTransferdto.getFromAccount()))
				.thenReturn(Optional.of(account1));
		Mockito.when(accountRepository.findByAccountNumber(fundTransferdto.getToAccount()))
				.thenReturn(Optional.empty());		
		Mockito.when(customerService.checkLoggingStatus(account1.getCustomer().getCustomerId()))
		.thenReturn(account1.getCustomer().getIsLoggedIn());
		
		assertThrows(AccountNotFoundException.class, () -> {
			fundService.fundTransfer(fundTransferdto);
		});
	}
	@Test
	public void testFundTransferBenificiaryNotFoundException() throws InsufficientResourcesException, AccountNotFoundException {
		
		Mockito.when( benificiaryRepository.findByUserIdAndAccountNumber(
               account1.getCustomer().getCustomerId(), fundTransferdto.getFromAccount()))
		.thenReturn(Optional.empty());
		Mockito.when(accountRepository.findByAccountNumber(fundTransferdto.getFromAccount()))
				.thenReturn(Optional.of(account1));
		Mockito.when(accountRepository.findByAccountNumber(fundTransferdto.getToAccount()))
				.thenReturn(Optional.of(account2));
		Mockito.when(customerService.checkLoggingStatus(account1.getCustomer().getCustomerId()))
				.thenReturn(account1.getCustomer().getIsLoggedIn());
	
		assertThrows(BenificiaryNotFoundException.class, () -> {
			fundService.fundTransfer(fundTransferdto);
		});

	}

}
