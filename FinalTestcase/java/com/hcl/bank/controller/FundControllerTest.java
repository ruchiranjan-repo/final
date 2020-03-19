package com.hcl.bank.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.hcl.bank.dto.FundTransferdto;
import com.hcl.bank.dto.Transactiondto;
import com.hcl.bank.entity.Account;
import com.hcl.bank.entity.Transaction;
import com.hcl.bank.exception.AccountNotFoundException;
import com.hcl.bank.response.TransactionResponse;
import com.hcl.bank.service.FundService;
import com.hcl.bank.service.TransactionService;

@RunWith(MockitoJUnitRunner.class)
public class FundControllerTest {
	
	@Mock
	FundService fundService;
	
	@Mock
	TransactionService transactionService;
	
	@InjectMocks
	FundController fundController;
	
	FundTransferdto fundTransferdto=null;
	Account account=null;
	
	@Before()
	public void before() {
		fundTransferdto=new FundTransferdto();
		fundTransferdto.setFromAccount(100L);
		fundTransferdto.setToAccount(100L);
		fundTransferdto.setAmount(400.0);
		fundTransferdto.setDescription("ok");		
	}
	
	@Test
	public void fundTransferTest() throws InsufficientResourcesException, AccountNotFoundException {
		String expected="fundTransferMessage";
		Mockito.when(fundService.fundTransfer(fundTransferdto)).thenReturn(expected);
		ResponseEntity<String> response=fundController.fundTransfer(fundTransferdto);
		assertEquals(expected, response.getBody());		
	}
	
	
	@Test
	public void fundTransferTestNegative() throws InsufficientResourcesException, AccountNotFoundException {
		
		fundTransferdto=new FundTransferdto();
		fundTransferdto.setFromAccount(200L);
		fundTransferdto.setToAccount(200L);
		fundTransferdto.setAmount(2000.0);
		fundTransferdto.setDescription(" not ok");	
		
		String expected="account not found,Insufficient fund";
		Mockito.when(fundService.fundTransfer(fundTransferdto)).thenReturn(expected);
		ResponseEntity<String> response=fundController.fundTransfer(fundTransferdto);
		assertEquals(expected, response.getBody());		
	}
	
	@Test
	public void getTransactionsStatement()  {
		TransactionResponse transactionResponse= new TransactionResponse();
		Transactiondto t = new Transactiondto();
		t.setTransactionId(1L);
		t.setAmount(new Double(100));
		t.setTransactionDateTime(LocalDateTime.now());
		t.setTransactionDescription("TransactionDescription");
		t.setTransactionStatus("SUCCESS");
		t.setTransactionType("DEBIT");

		
		List<Transactiondto> transactions = new ArrayList<Transactiondto>();
		transactions.add(t);
		
		transactionResponse.setSizeOfList(1);
		transactionResponse.setFundTransfer(transactions);
		Mockito.when(transactionService.getTransactionsStatement(1L)).thenReturn(transactionResponse);
		ResponseEntity<TransactionResponse> response=fundController.getTransactionsStatement(1L);
		assertEquals(response.getBody().getSizeOfList(), new Integer(1));
		assertEquals(response.getBody().getFundTransfer().get(0).getTransactionId(), t.getTransactionId());
		assertEquals(response.getBody().getFundTransfer().get(0).getTransactionDescription(), t.getTransactionDescription());
		assertEquals(response.getBody().getFundTransfer().get(0).getTransactionType(), t.getTransactionType());
	}
	
	
	
	
	

}
