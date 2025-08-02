package com.pavansharma.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pavansharma.controller.ProductController;
import com.pavansharma.entity.Credentials;
import com.pavansharma.repository.LoginRepository;

@Service
public class LoginServices {
	
	@Autowired
	LoginRepository loginRepo;
	
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	public Credentials getUsernameAndPassword(String username) {
		logger.info("Inside getUsernameAndPassword() method");
		Credentials cred =  loginRepo.getLoginInfo(username);
		logger.info("Credentials fetched successfully");
		return cred;
	}

}
