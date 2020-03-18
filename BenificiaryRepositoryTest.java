package com.hcl.bank.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.hcl.bank.entity.Benificiary;
import com.hcl.bank.entity.Customer;

@DataJpaTest
public class BenificiaryRepositoryTest {
	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	BenificiaryRepository benificiaryRepository;

	Benificiary benificiary;
	Customer customer;

	@BeforeEach
	public void setup() {
		
		benificiary = new Benificiary();
		benificiary.setBenificiaryAccountNumber(100L);
		benificiary.setBenificiaryName("BENIFICIARY1");
		customer = new Customer();
		customer.setCustomerName("TEST_CUSTOMER");
		benificiary.setCustomer(customer);
	}
	
	@Test
	public void testFindByCustomerId()
	{
		Benificiary savedBenificiary=testEntityManager.persistAndFlush(benificiary);
		
		Benificiary b = benificiaryRepository.findByCustomerId(savedBenificiary.getCustomer().getCustomerId());
		
		assertThat(b.getCustomer().getCustomerId()).isEqualTo(savedBenificiary.getCustomer().getCustomerId());
		assertThat(b.getCustomer().getCustomerName()).isEqualTo("TEST_CUSTOMER");
		assertThat(b.getBenificiaryAccountNumber()).isEqualTo(100L);
		assertThat(b.getBenificiaryName()).isEqualTo("BENIFICIARY1");
		
	}
	@Test
	public void testFindByCustomerIdNotFound()
	{
		testEntityManager.persistAndFlush(benificiary);
		
		Benificiary b = benificiaryRepository.findByCustomerId(3L);
		
		assertNull(b);
		
		
	}

}
