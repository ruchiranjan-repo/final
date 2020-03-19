package com.hcl.bank.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcl.bank.dto.BenificiaryDTO;
import com.hcl.bank.dto.BenificiaryRequest;
import com.hcl.bank.entity.Account;
import com.hcl.bank.entity.Benificiary;
import com.hcl.bank.entity.Customer;
import com.hcl.bank.exception.AccountNotFoundException;
import com.hcl.bank.exception.BenificiaryNotFoundException;
import com.hcl.bank.exception.CustomerNotLoggedInException;
import com.hcl.bank.exception.UserNotFoundException;
import com.hcl.bank.service.BenificiaryService;

@SpringBootTest
public class BenificiaryControllerTest {

	@MockBean
	BenificiaryService benificiaryService;

	@Autowired
	BenificiaryController beneificaryController;

	Benificiary beneficiary = null;
	BenificiaryDTO benificiaryDTO = null;
	BenificiaryDTO benificiaryDTO2 = null;
	BenificiaryRequest benificiaryRequest = null;

	Account account = null;

	@Before
	public void add() {

		/*
		 * Customer customer = new Customer(); customer.setCustomerId(1L);
		 * 
		 * beneficiary.setBenificiaryAccountNumber(1L);
		 * beneficiary.setBenificiaryName("ram"); beneficiary.setCustomer(customer);
		 */
		benificiaryRequest = new BenificiaryRequest();
		beneficiary = new Benificiary();
		
		Customer customer = new Customer();
		customer.setCustomerId(1000L);
		benificiaryRequest.setAccountNumber(1L);
		benificiaryRequest.setName("ram");
		benificiaryRequest.setCustomerId(4L);
		beneficiary.setBenificiaryId(1L);
		beneficiary.setBenificiaryAccountNumber(1000L);
		beneficiary.setBenificiaryName("vasu");
		beneficiary.setCustomer(customer);

		account = new Account();
		account.setAccountNumber(2000L);
		
		benificiaryDTO=new BenificiaryDTO();
		
	}

	@Test
	public void testAddBenificiary()
			throws BeansException, UserNotFoundException, AccountNotFoundException, CustomerNotLoggedInException {
		String expected = "Benificiary added successfully.";
		Mockito.when(benificiaryService.addBenificiary(benificiaryRequest)).thenReturn(expected);
		ResponseEntity<String> response = beneificaryController.addBenificiary(benificiaryRequest);
		assertEquals(expected, response.getBody());

	}

	@Test
	public void testUpdateBenificiary()
			throws BenificiaryNotFoundException, UserNotFoundException, AccountNotFoundException {

		benificiaryDTO = new BenificiaryDTO();
		benificiaryDTO.setBenificiaryAccountNumber(1L);
		benificiaryDTO.setBenificiaryName("ram");
		benificiaryDTO.setCustomerId(1L);

		String expected = "updated";
		Mockito.when(benificiaryService.updatedBenificiary(benificiaryDTO)).thenReturn(expected);
		ResponseEntity<String> response = beneificaryController.updateBenificiary(benificiaryDTO);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateBenificiaryNegative()
			throws BenificiaryNotFoundException, UserNotFoundException, AccountNotFoundException {

		benificiaryDTO = new BenificiaryDTO();
		benificiaryDTO.setBenificiaryAccountNumber(100L);
		benificiaryDTO.setBenificiaryName("ram");
		benificiaryDTO.setCustomerId(100L);

		
		String exp = "account not found exception";
		Mockito.when(benificiaryService.updatedBenificiary(benificiaryDTO)).thenReturn(exp);
		ResponseEntity<String> response = beneificaryController.updateBenificiary(benificiaryDTO);
		System.out.println(response.getBody());
		assertEquals(exp, response.getBody());
	}

	@Test
	public void testDelete() throws BenificiaryNotFoundException {
		
		
		Long benificiaryId = 1L;
		String expected = "Benificiar Deleted Successfully";
		Mockito.when(benificiaryService.deleteBenificiary(benificiaryId)).thenReturn(expected);
		ResponseEntity<String> response = beneificaryController.deleteBenificiary(1L);
		assertEquals(expected, response.getBody());
	}
	
	@Test
	public void testDeleteNegative() throws BenificiaryNotFoundException {
		
		
		Long benificiaryId = 2000L;
		String expected = "Benificiary   Deleted Successfully";
		Mockito.when(benificiaryService.deleteBenificiary(benificiaryId)).thenReturn("benificiary not found exception");
		ResponseEntity<String> response = beneificaryController.deleteBenificiary(2000L);
		assertNotEquals(expected, response.getBody());
	}
	

	@Test
	public void testGetBenificiaryByBenificiaryIdPositive() throws BenificiaryNotFoundException {
		
		benificiaryDTO = new BenificiaryDTO();
		benificiaryDTO.setBenificiaryAccountNumber(1L);
		benificiaryDTO.setBenificiaryName("ram");
		benificiaryDTO.setCustomerId(1L);
		
		Long benificiaryId = 1L;
		Mockito.when(benificiaryService.getBenificiaryByBenificiaryId(benificiaryId)).thenReturn(benificiaryDTO);
		ResponseEntity<Object> response = beneificaryController.getBenificiaryByBenificiaryId(benificiaryId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	

}
