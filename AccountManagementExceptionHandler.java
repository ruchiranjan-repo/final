package com.demobank.accountmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountManagementExceptionHandler {
	
	
	@ExceptionHandler(value = BenificiaryNotFoundException.class)
	   public ResponseEntity<Object> benificiaryNotFoundExceptionHandler(BenificiaryNotFoundException benificiaryNotFoundException) {
	      return new ResponseEntity<>("Benificiary with provided  id not found.", HttpStatus.NOT_FOUND);
	   }
	
	@ExceptionHandler(value = UserNotFoundException.class)
	   public ResponseEntity<Object> userNotFoundExceptionHandler(UserNotFoundException userNotFoundException) {
	      return new ResponseEntity<>("User with provided  id not found.", HttpStatus.NOT_FOUND);
	   }

	
	@ExceptionHandler(value = CustomerNotLoggedInException.class)
	   public ResponseEntity<Object> CustomerNotLoggedInExceptionHandler(CustomerNotLoggedInException customerNotLoggedInException) {
	      return new ResponseEntity<>("Please login first.", HttpStatus.FORBIDDEN);
	   }



}
