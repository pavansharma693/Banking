package com.pavansharma.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pavansharma.entity.Account;
import com.pavansharma.entity.Credentials;

@Repository
public interface LoginRepository extends CrudRepository<Credentials, Integer>{
		
		@Query(value = "select username,password,account_number,name from login where username = ?1", nativeQuery = true)
		Credentials getLoginInfo(String username);

	}

