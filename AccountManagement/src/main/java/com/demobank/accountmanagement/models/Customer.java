package com.demobank.accountmanagement.models;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.List;

@Entity
@Table(name="Customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long customerId ;
	
	@Column(name="customerName")
	String customerName;
	
	@Column(name="customerEmailId")
	String customerEmailId;
	
	@Column(name="customerMobileNUmber")
	Long customerMobileNumber;
	
	@Column(name="customerDOB")
	LocalDate customerDOB;
	
	@Column(name="customerAddress")
	String customerAddress;
	@Column(name="password")
	String password;
	@Column(name="isLoggedIn")
    Boolean isLoggedIn;
	@OneToMany(mappedBy="customer",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	List<Benificiary> benificiaries;
	
	
	@OneToMany(mappedBy="customer",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	List<Account> accounts;
	
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmailId() {
		return customerEmailId;
	}
	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	public Long getCustomerMobileNumber() {
		return customerMobileNumber;
	}
	public void setCustomerMobileNumber(Long customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}
	public LocalDate getCustomerDOB() {
		return customerDOB;
	}
	public void setCustomerDOB(LocalDate customerDOB) {
		this.customerDOB = customerDOB;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getIsLoggedIn() {
		return isLoggedIn;
	}
	public void setIsLoggedIn(Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	public List<Benificiary> getBenificiaries() {
		return benificiaries;
	}
	public void setBenificiaries(List<Benificiary> benificiaries) {
		this.benificiaries = benificiaries;
	}


}
