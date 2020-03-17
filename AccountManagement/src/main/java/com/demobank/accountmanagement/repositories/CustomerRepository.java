package com.demobank.accountmanagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demobank.accountmanagement.models.Customer;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByCustomerEmailIdAndPassword(String emailId, String password);

	Optional<Customer> findByCustomerId(Long customerId);
	
	

	

}
