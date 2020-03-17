package com.demobank.accountmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demobank.accountmanagement.models.FundTransfer;
@Repository
public interface FundTransferRepository extends JpaRepository<FundTransfer,Long>{
	

}
