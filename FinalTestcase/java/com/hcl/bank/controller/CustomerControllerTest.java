package com.hcl.bank.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.hcl.bank.entity.Customer;
import com.hcl.bank.exception.UserNotFoundException;
import com.hcl.bank.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
	
	@Mock
	CustomerService customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	@Before
	public void before()  {
		
		Customer customer=new Customer();
		customer.setCustomerEmailId("vasu@gmail.com");
		customer.setPassword("vasu");
		
	}
	
	@Test
	public void testCheckLogin() throws UserNotFoundException {
		String expected="customerLogin";
		Mockito.when(customerService.customerLogin("vasu@gmail.com", "vasu")).thenReturn(expected);
		ResponseEntity<String> response =customerController.customerLogin("vasu@gmail.com", "vasu");
		assertEquals(expected,response.getBody());
		
	}
	
	@Test
	public void testCheckLogout() throws UserNotFoundException {
		String expected="Logout sccessful";
		Mockito.when(customerService.customerLogout("vasu@gmail.com")).thenReturn(expected);
		ResponseEntity<String> response =customerController.customerLogout("vasu@gmail.com");
		assertEquals(expected,response.getBody());
		
	}
	
	
	
	
	
	
	
	

}

