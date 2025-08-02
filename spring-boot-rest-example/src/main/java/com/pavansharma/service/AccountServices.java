package com.pavansharma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pavansharma.controller.ProductController;
import com.pavansharma.entity.*;
import com.pavansharma.interfaces.Banks;
import com.pavansharma.repository.AccountRepository;
import com.pavansharma.repository.AxisRepository;
import com.pavansharma.repository.HdfcRepository;
import com.pavansharma.repository.IciciRepository;

import org.slf4j.*;

@Service
@Component
public class AccountServices {
	
	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
	AxisRepository axisRepo;
	
	@Autowired
	HdfcRepository hdfcRepo;
	
	@Autowired
	IciciRepository iciciRepo;
	
	Logger logger = LoggerFactory.getLogger(AccountServices.class);
	
	public Account save(Account account) {
		return accountRepo.save(account);
	}
	
	/*public Account getUserByAccountNumber(int accountNumber) {
		return */
	
	public Iterable<Account> getAllUsers() {
		return accountRepo.findAll();
	}
	
	public Account getAllNonKYCUsers() {
		logger.info("Inside getAllNonKYCUsers() method");
		Account account =  accountRepo.getAllNonKYCUsers();
		logger.info("All Non KYC Users are : " +account.getName());
		return account;
	}
	
	public Account getAmount(String name) {
		try {
			Account account = accountRepo.getAmount(name);
			return account;
		}catch(Exception e) {
			System.out.println("Error = " +e.getStackTrace());
		}
		return null;
	}
	
	public Banks getCreditorBalance(String accountNumber, Transaction trans) {
		logger.info("Inside getCreditorBalance() method");
		Banks account = null;
		switch(trans.getBank_name()) {
		case "AXIS Bank" :
			account = axisRepo.getCreditorAmountFromAXIS(accountNumber);
			logger.info("Account Number " +accountNumber+ " has balance " +account.toString());
			break;
		case "HDFC Bank" :
			account = hdfcRepo.getCreditorAmountFromHDFC(accountNumber);
			logger.info("Account Number " +accountNumber+ " has balance " +account.toString());
			break;
		case "ICICI Bank" :
			account = iciciRepo.getCreditorAmountFromICICI(accountNumber);
			logger.info("Account Number " +accountNumber+ " has balance " +account.toString());
			break;
		}
		return account;
		}
		
	
	public Banks getDebitorBalance(String accountNumber,Transaction trans) {
		logger.info("Inside getDebitorBalance() method");
		Banks account = null;
		switch(trans.getBank_name()) {
		case "AXIS Bank" :
			account = axisRepo.getDebitorAmountFromAXIS(accountNumber);
			logger.info("Account Number " +accountNumber+ " has balance " +account.toString());
			break;
		case "HDFC Bank" :
			account = hdfcRepo.getDebitorAmountFromHDFC(accountNumber);
			logger.info("Account Number " +accountNumber+ " has balance " +account.toString());
			break;
		case "ICICI Bank" :
			account = iciciRepo.getDebitorAmountFromICICI(accountNumber);
			logger.info("Account Number " +accountNumber+ " has balance " +account.toString());
			break;
		}
		return account;
		}
	
	
	@Transactional
	public Banks saveAxisTransaction(Axis Credit, Axis Debit){
		logger.info("Inside saveAxisTransaction() method");
		axisRepo.save(Credit);
		accountRepo.saveCredit(String.valueOf(Credit.getBalance()), Credit.getNumber());
		accountRepo.saveDebit(String.valueOf(Debit.getBalance()), Debit.getNumber());
		logger.info("Amount " +Credit.getBalance()+ " is Credited successfully to User" +Credit.getName());
		logger.info("Amount  " +Debit.getBalance()+ " is  Debited successfully to User" +Debit.getName());
		return axisRepo.save(Debit);
	}
	
	@Transactional
	public Banks saveHdfcTransaction(Hdfc Credit, Hdfc Debit){
		logger.info("Inside saveHdfcTransaction() method");
		accountRepo.saveCredit(String.valueOf(Credit.getBalance()), Credit.getNumber());
		accountRepo.saveDebit(String.valueOf(Debit.getBalance()), Debit.getNumber());
		hdfcRepo.save(Credit);
		logger.info("Amount " +Credit.getBalance()+ " is Credited successfully to User" +Credit.getName());
		logger.info("Amount  " +Debit.getBalance()+ " is  Debited successfully to User" +Debit.getName());
		return hdfcRepo.save(Debit);
	}
	
	@Transactional
	public Banks saveIciciTransaction(Icici Credit, Icici Debit){
		logger.info("Inside saveIciciTransaction() method");
		iciciRepo.save(Credit);
		accountRepo.saveCredit(String.valueOf(Credit.getBalance()), Credit.getNumber());
		accountRepo.saveDebit(String.valueOf(Debit.getBalance()), Debit.getNumber());
		logger.info("Amount " +Credit.getBalance()+ " is Credited successfully to User" +Credit.getName());
		logger.info("Amount  " +Debit.getBalance()+ " is  Debited successfully to User" +Debit.getName());
		return iciciRepo.save(Debit);
	}
	
	@Transactional
	public Account debitAmount(Account debitorAccount, String bank_name) {
		logger.info("Inside debitAmount() method");
		Account account = accountRepo.save(debitorAccount);
		logger.info("Amount " +account.getBalance()+ " will be Debited");
		return account;
	}
	
	public Account getListOfBanks(String accountNumber) {
		logger.info("Inside getListOfBanks() method");
		Account account = accountRepo.getListOfBanks(accountNumber);
		logger.info("List of Bank Fetched for Account Number " +accountNumber+ " is " +account.getBank_name());
		return account;
	}
	

	
}
