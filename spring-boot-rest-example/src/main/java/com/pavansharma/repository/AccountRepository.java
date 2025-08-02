package com.pavansharma.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pavansharma.entity.Account;
import com.pavansharma.entity.Axis;
import com.pavansharma.interfaces.Banks;


@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>{

	@Query(value = "select account_number,name,balance,kyc,bank_name from account where kyc = 'No'", nativeQuery = true)
	Account getAllNonKYCUsers();
	
	@Query(value = "select account_number,name,balance,kyc,bank_name from account where name = :name", nativeQuery = true)
	Account getAmount(@Param("name")String accountNumber);
	
	@Query(value = "select account_number,name,balance,kyc,bank_name from account where account_number = :account_number", nativeQuery = true)
	Account getListOfBanks(@Param("account_number")String account_number);
	
	@Transactional
	@Modifying
	@Query(value = "update account set balance = :balance where account_number = :account_number", nativeQuery = true)
	Integer saveCredit(@Param("balance")String balance, @Param("account_number")String account_number);

	@Transactional
	@Modifying
	@Query(value = "update account set balance = :balance where account_number = :account_number", nativeQuery = true)
	Integer saveDebit(@Param("balance")String balance, @Param("account_number")String account_number);
	
}
