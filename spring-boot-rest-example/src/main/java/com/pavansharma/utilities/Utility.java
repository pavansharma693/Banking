package com.pavansharma.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pavansharma.entity.Account;
import com.pavansharma.entity.Transaction;
import com.pavansharma.interfaces.Banks;
import com.pavansharma.service.AccountServices;


public class Utility {

	@Autowired
	AccountServices accountServices;
	
	public Banks getCreditorBalance(Transaction trans) {
		return accountServices.getCreditorBalance(trans.getReceiverAccountNumber(), trans);
	}
	
	public Banks getDebitorBalance(Transaction trans) {
		return accountServices.getDebitorBalance(trans.getReceiverAccountNumber(), trans);
	}
		
	
}
