package com.demobank.accountmanagement.services;

import com.demobank.accountmanagement.dtos.BenificiaryDTO;
import com.demobank.accountmanagement.dtos.BenificiaryRequest;

public interface BenificiaryService {
	
	public void addBenificiary(BenificiaryRequest  benificiaryRequest);
	
	public void updatedBenificiary(BenificiaryDTO benificiaryRequest);
	
	public void deleteBenificiary (Long benificiaryId);
	
	public BenificiaryDTO getBenificiaryByBenificiaryId(Long benificiaryId);
	
	
	

}
