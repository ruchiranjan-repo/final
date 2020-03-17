package com.demobank.accountmanagement.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demobank.accountmanagement.dtos.BenificiaryDTO;
import com.demobank.accountmanagement.dtos.BenificiaryRequest;
import com.demobank.accountmanagement.exception.BenificiaryNotFoundException;
import com.demobank.accountmanagement.services.BenificiaryServiceImpl;


@RestController
public class BenificiaryRestController {
	
	@Autowired	
	BenificiaryServiceImpl benificiaryService;
	
	@RequestMapping(value="/benificiaries", method=RequestMethod.POST)
	public ResponseEntity<Object> addBenificiary(@RequestBody BenificiaryRequest benificiaryRequest)
	{
		
		benificiaryService.addBenificiary(benificiaryRequest);		
		return new ResponseEntity<>("Benificiary added successfully.",HttpStatus.CREATED);		
	}
	
	@RequestMapping(value="/benificiaries", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateBenificiary(BenificiaryDTO benificiaryRequest)
	{
		
		benificiaryService.updatedBenificiary(benificiaryRequest);		
		return new ResponseEntity<>("Benificiary updated successfully.",HttpStatus.OK);		
	}
	 @RequestMapping(value = "/benificiaries/{benificiaryId}", method = RequestMethod.DELETE)
	   public ResponseEntity<Object> deleteBenificiary(@PathVariable("benificiaryId") Long benificiaryId)  throws BenificiaryNotFoundException {	     
		 benificiaryService.deleteBenificiary(benificiaryId);
	     return new ResponseEntity<>("Customer Deleted Successfully", HttpStatus.NO_CONTENT);
	   }
	 @RequestMapping(value = "/benificiaries/{benificiaryId}", method = RequestMethod.GET)
	   public ResponseEntity<Object> getBenificiaryByBenificiaryId(@PathVariable("benificiaryId") Long benificiaryId)  throws BenificiaryNotFoundException {	     
		 BenificiaryDTO benificiaryDTO= benificiaryService.getBenificiaryByBenificiaryId(benificiaryId);
	     return new ResponseEntity<>(benificiaryDTO, HttpStatus.OK);
	   }

}
