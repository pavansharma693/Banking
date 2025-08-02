package com.pavansharma.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pavansharma.entity.Icici;

@Repository
public interface IciciRepository extends CrudRepository<Icici, Integer>{
	
	@Query(value = "select account_number,name,balance,bank_name,kyc from icici_bank where account_number = :accountNumber", nativeQuery = true)
	Icici getCreditorAmountFromICICI(@Param("accountNumber")String accountNumber );
	
	@Query(value = "select account_number,name,balance,bank_name,kyc from icici_bank where account_number = :accountNumber", nativeQuery = true)
	Icici getDebitorAmountFromICICI(@Param("accountNumber")String accountNumber );
	
	@Transactional
	@Modifying
	@Query(value = "update icici_bank set balance = :amount where account_number = :accountNumber" , nativeQuery = true)
	Integer creditAmountICICI(String amount, String accountNumber);
	
	@Transactional
	@Modifying
	@Query(value = "update icici_bank set balance = :amount where account_number = :accountNumber" , nativeQuery = true)
	Integer debitAmountICICI(String accountNumber, String amount);
}
