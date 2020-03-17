package com.demobank.accountmanagement.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demobank.accountmanagement.dtos.CustomerDto;
import com.demobank.accountmanagement.exception.UserNotFoundException;
import com.demobank.accountmanagement.models.Customer;
import com.demobank.accountmanagement.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public String customerLogin(String emailId, String password) throws UserNotFoundException {

		Optional<Customer> customer = customerRepository.findByCustomerEmailIdAndPassword(emailId, password);

		if (!customer.isPresent()) {
			throw new UserNotFoundException();
		}

		customer.get().setIsLoggedIn(true);

		customerRepository.save(customer.get());

		System.out.println(customer.get().getIsLoggedIn());

		return "login successfull";
	}

	@Override
	public CustomerDto findCustomerByCustomerId(Long customerId) throws UserNotFoundException {

		Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
		if (!customer.isPresent()) {
			throw new UserNotFoundException();
		} 
		CustomerDto customerDto = new CustomerDto();
		BeanUtils.copyProperties(customer, customerDto);
		return customerDto;
	}
}
