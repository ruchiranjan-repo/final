package com.hcl.bank.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcl.bank.entity.Benificiary;
import com.hcl.bank.entity.Customer;

@DataJpaTest
@RunWith(SpringJUnit4ClassRunner.class)
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
		customer.setCustomerAddress("CUSTOMER ADDRESS");
		customer.setCustomerDOB(LocalDate.now());
		customer.setCustomerEmailId("customerTest@gmail.com");
		customer.setCustomerMobileNumber(1111111111L);
		customer.setIsLoggedIn(false);
		customer.setPassword("password");
		benificiary.setCustomer(customer);
	}

	@Test
	public void testFindByCustomerId() {
		Benificiary savedBenificiary = testEntityManager.persistAndFlush(benificiary);

		Benificiary b = benificiaryRepository.findByCustomerId(savedBenificiary.getCustomer().getCustomerId());

		assertThat(b.getCustomer().getCustomerId()).isEqualTo(savedBenificiary.getCustomer().getCustomerId());
		assertThat(b.getCustomer().getCustomerName()).isEqualTo("TEST_CUSTOMER");
		assertThat(b.getBenificiaryAccountNumber()).isEqualTo(100L);
		assertThat(b.getBenificiaryName()).isEqualTo("BENIFICIARY1");

	}

	@Test
	public void testFindByCustomerIdNotFound() {
		testEntityManager.persistAndFlush(benificiary);

		Benificiary b = benificiaryRepository.findByCustomerId(3L);

		assertNull(b);

	}

	@Test
	public void testFindByUserIdAndAccountNumber() {
		Benificiary savedbenificiary=testEntityManager.persistAndFlush(benificiary);

		Optional<Benificiary> b = benificiaryRepository.findByUserIdAndAccountNumber(100L, savedbenificiary.getCustomer().getCustomerId());

		assertThat(b.get().getCustomer().getCustomerId()).isEqualTo(savedbenificiary.getCustomer().getCustomerId());
		assertThat(b.get().getCustomer().getCustomerName()).isEqualTo("TEST_CUSTOMER");
		assertThat(b.get().getBenificiaryAccountNumber()).isEqualTo(100L);
		assertThat(b.get().getBenificiaryName()).isEqualTo("BENIFICIARY1");

	}

	@Test
	public void testFindByUserIdAndAccountNumberNotFound() {
		testEntityManager.persistAndFlush(benificiary);

		Optional<Benificiary> b = benificiaryRepository.findByUserIdAndAccountNumber(10L, 2L);

		assertFalse(b.isPresent());

	}
}
