package com.demobank.accountmanagement.dtos;

public class BenificiaryDTO {
	Long BenificiaryId;
	Long benificiaryAccountNumber;
	String BenificiaryName;
	Long CustomerId;

	public Long getBenificiaryId() {
		return BenificiaryId;
	}

	public void setBenificiaryId(Long benificiaryId) {
		BenificiaryId = benificiaryId;
	}

	public Long getBenificiaryAccountNumber() {
		return benificiaryAccountNumber;
	}

	public void setBenificiaryAccountNumber(Long benificiaryAccountNumber) {
		this.benificiaryAccountNumber = benificiaryAccountNumber;
	}

	public String getBenificiaryName() {
		return BenificiaryName;
	}

	public void setBenificiaryName(String benificiaryName) {
		BenificiaryName = benificiaryName;
	}

	public Long getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(Long customerId) {
		CustomerId = customerId;
	}
	

}
