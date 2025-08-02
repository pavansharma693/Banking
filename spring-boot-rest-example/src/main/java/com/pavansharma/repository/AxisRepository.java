package com.pavansharma.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pavansharma.entity.Axis;

@Repository
public interface AxisRepository extends CrudRepository<Axis, Integer>{

	@Query(value = "select account_number,name,balance,bank_name,kyc from axis_bank where account_number = :accountNumber", nativeQuery = true)
	Axis getCreditorAmountFromAXIS(@Param("accountNumber")String accountNumber );
	
	@Query(value = "select account_number,name,balance,bank_name,kyc from axis_bank where account_number = :accountNumber", nativeQuery = true)
	Axis getDebitorAmountFromAXIS(@Param("accountNumber")String accountNumber );
	
	@Transactional
	@Modifying
	@Query(value = "update axis_bank set balance = :amount where account_number = :accountNumber" , nativeQuery = true)
	Integer creditAmountAXIS(String amount, String accountNumber);
	
	@Transactional
	@Modifying
	@Query(value = "update axis_bank set balance = :amount where account_number = :accountNumber" , nativeQuery = true)
	Integer debitAmountAXIS(String accountNumber, String amount);
}
