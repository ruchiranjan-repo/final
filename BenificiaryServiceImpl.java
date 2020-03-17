package com.demobank.accountmanagement.services;

import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demobank.accountmanagement.dtos.BenificiaryDTO;
import com.demobank.accountmanagement.dtos.BenificiaryRequest;
import com.demobank.accountmanagement.dtos.CustomerDto;
import com.demobank.accountmanagement.exception.BenificiaryNotFoundException;
import com.demobank.accountmanagement.exception.CustomerNotLoggedInException;
import com.demobank.accountmanagement.exception.UserNotFoundException;
import com.demobank.accountmanagement.models.Benificiary;
import com.demobank.accountmanagement.models.Customer;
import com.demobank.accountmanagement.repositories.BenificiaryRepository;

@Service
public class BenificiaryServiceImpl implements BenificiaryService {
	@Autowired
	BenificiaryRepository benificiaryRepository;

	@Autowired
	CustomerService customerService;
	
	
	Logger log = Logger.getLogger(BenificiaryServiceImpl.class);

	@Override
	public void addBenificiary(BenificiaryRequest benificiaryRequest) throws UserNotFoundException,CustomerNotLoggedInException {
		if(customerService.checkLoggingStatus(benificiaryRequest.getCustomerId()));
		{
		Benificiary benificiary = new Benificiary();
		benificiary.setBenificiaryAccountNumber(benificiaryRequest.getAccountNumber());
		benificiary.setBenificiaryName(benificiaryRequest.getName());
		CustomerDto customerDto = customerService.findCustomerByCustomerId(benificiaryRequest.getCustomerId());
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDto, customer);
		benificiary.setCustomer(customer);

		benificiaryRepository.save(benificiary);
		log.info("Benificiary added successfully.");
		}
		throw new CustomerNotLoggedInException();
		
	}

	@Override
	public void updatedBenificiary(BenificiaryDTO benificiaryRequest)
			throws BenificiaryNotFoundException, UserNotFoundException {
		if(customerService.checkLoggingStatus(benificiaryRequest.getCustomerId()));
		{
		Optional<Benificiary> benificiary = benificiaryRepository
				.findByBenificiaryId(benificiaryRequest.getBenificiaryId());
		if (benificiary.isPresent()) {

			benificiary.get().setBenificiaryAccountNumber(benificiaryRequest.getBenificiaryAccountNumber());
			benificiary.get().setBenificiaryName(benificiaryRequest.getBenificiaryName());

			CustomerDto customerDto = customerService.findCustomerByCustomerId(benificiaryRequest.getCustomerId());

			Customer customer = new Customer();

			BeanUtils.copyProperties(customerDto, customer);
			benificiary.get().setCustomer(customer);

			benificiaryRepository.save(benificiary.get());
			log.info("Benificiary added successfully.");
		} else {
			throw new BenificiaryNotFoundException();
		}
		}
		throw new CustomerNotLoggedInException();
		

	}

	@Override
	public void deleteBenificiary(Long benificiaryId) throws BenificiaryNotFoundException {
		
		Optional<Benificiary> benificiary = benificiaryRepository.findByBenificiaryId(benificiaryId);
		if (benificiary.isPresent()) {
			benificiaryRepository.deleteById(benificiary.get().getBenificiaryId());
		} else {
			throw new BenificiaryNotFoundException();
		}
		
	}

	@Override
	public BenificiaryDTO getBenificiaryByBenificiaryId(Long benificiaryId) throws BenificiaryNotFoundException {
		Optional<Benificiary> benificiary = benificiaryRepository.findByBenificiaryId(benificiaryId);
		if (benificiary.isPresent()) {
			BenificiaryDTO benificiaryDto = new BenificiaryDTO();
			benificiaryDto.setBenificiaryId(benificiary.get().getBenificiaryId());
			benificiaryDto.setBenificiaryAccountNumber(benificiary.get().getBenificiaryAccountNumber());
			benificiaryDto.setBenificiaryName(benificiary.get().getBenificiaryName());
			benificiaryDto.setCustomerId(benificiary.get().getCustomer().getCustomerId());
			return benificiaryDto;
		} else {
			throw new BenificiaryNotFoundException();
		}
	}

	/*
	 * @Override public List<BenificiaryDTO> getAllBenificiaryByCustomerId(Long
	 * customerId) throws BenificiaryNotFoundException { List<Benificiary>
	 * benificiary = benificiaryRepository.findByCustomer(customerId));
	 * if(CollectionUtils.isEmpty(benificiary)) {
	 * 
	 * }
	 * 
	 * 
	 * return benificiary; }
	 */
}
