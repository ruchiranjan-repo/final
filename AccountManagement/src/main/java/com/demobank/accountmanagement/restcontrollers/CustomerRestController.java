package com.demobank.accountmanagement.restcontrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demobank.accountmanagement.exception.UserNotFoundException;
import com.demobank.accountmanagement.services.CustomerService;



@RestController
@RequestMapping("/customers")
public class CustomerRestController {

	private Logger logger = LoggerFactory.getLogger(CustomerRestController.class);

	@Autowired
	private CustomerService customerService;

	@PostMapping("/login")
	public ResponseEntity<String> customerLogin(@RequestParam(required = true) String emailId,
			@RequestParam(required = true) String password) throws UserNotFoundException {

		
		logger.info("user login");
		
		String customerLogin = customerService.customerLogin(emailId, password);

		return new ResponseEntity<String>(customerLogin, HttpStatus.OK);
	}
}
