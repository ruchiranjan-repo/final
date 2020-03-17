package com.demobank.accountmanagement.services;

import com.demobank.accountmanagement.dtos.FundTransferReqdto;

public interface FundService {
	
	public void fundTransfer(FundTransferReqdto transferReqdto);

}
