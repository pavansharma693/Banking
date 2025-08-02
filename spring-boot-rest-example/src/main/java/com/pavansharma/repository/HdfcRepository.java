package com.pavansharma.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pavansharma.entity.Hdfc;

@Repository
public interface HdfcRepository extends CrudRepository<Hdfc, Integer>{

	@Query(value = "select account_number,name,balance,bank_name,kyc from hdfc_bank where account_number = :accountNumber", nativeQuery = true)
	Hdfc getCreditorAmountFromHDFC(@Param("accountNumber")String accountNumber );
	
	@Query(value = "select account_number,name,balance,bank_name,kyc from hdfc_bank where account_number = :accountNumber", nativeQuery = true)
	Hdfc getDebitorAmountFromHDFC(@Param("accountNumber")String accountNumber );
	
	@Transactional
	@Modifying
	@Query(value = "update hdfc_bank set balance = :amount where account_number = :accountNumber" , nativeQuery = true)
	Integer creditAmountHDFC(String amount, String accountNumber);
	
	@Transactional
	@Modifying
	@Query(value = "update hdfc_bank set balance = :amount where account_number = :accountNumber" , nativeQuery = true)
	Integer debitAmountHDFC(String accountNumber, String amount);
	
}
