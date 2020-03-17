package com.demobank.accountmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demobank.accountmanagement.models.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{

	Account findByAccountNumber(Long fromAccountNumber);

}