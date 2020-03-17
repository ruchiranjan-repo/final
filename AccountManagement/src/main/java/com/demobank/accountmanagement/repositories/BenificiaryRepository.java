package com.demobank.accountmanagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demobank.accountmanagement.models.Benificiary;

@Repository
public interface BenificiaryRepository extends JpaRepository<Benificiary, Long>{
	
		
	Optional<Benificiary> findByBenificiaryId(Long benificiaryId);
	

}
