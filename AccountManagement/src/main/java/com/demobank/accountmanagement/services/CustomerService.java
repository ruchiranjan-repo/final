package com.demobank.accountmanagement.services;

import com.demobank.accountmanagement.dtos.CustomerDto;
import com.demobank.accountmanagement.exception.UserNotFoundException;

public interface CustomerService {
	public String customerLogin(String emailId, String password) throws UserNotFoundException;
	public CustomerDto findCustomerByCustomerId(Long customerId) throws UserNotFoundException;
}
