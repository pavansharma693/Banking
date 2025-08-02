package com.pavansharma.controller;

import java.util.List;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavansharma.entity.Account;
import com.pavansharma.entity.Axis;
import com.pavansharma.entity.Credentials;
import com.pavansharma.entity.Hdfc;
import com.pavansharma.entity.Icici;
import com.pavansharma.entity.Product;
import com.pavansharma.entity.Transaction;
import com.pavansharma.exceptions.ResourceNotFoundException;
import com.pavansharma.interfaces.Banks;
import com.pavansharma.interfaces.IProductService;
import com.pavansharma.repository.AxisRepository;
import com.pavansharma.repository.HdfcRepository;
import com.pavansharma.repository.IciciRepository;
import com.pavansharma.service.AccountServices;
import com.pavansharma.service.LoginServices;
import com.pavansharma.utilities.Utility;


@RestController
@EnableTransactionManagement
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
	
	public static final String AXIS_BANK = "AXIS Bank";
	
	public static final String ICICI_BANK = "ICICI Bank";
	
	public static final String HDFC_BANK= "HDFC Bank";
	
	@Autowired
	IProductService productService;
	
	@Autowired
	AccountServices accountServices;
	
	@Autowired
	LoginServices loginServices;
	
	@Autowired
	AxisRepository axisRepo;
	
	@Autowired
	HdfcRepository hdfcRepo;
	
	@Autowired
	IciciRepository iciciRepo;
	
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	

	@GetMapping("/product")
	public List<Product> getProduct() {
		logger.info("Inside method getProduct() of class " +this.getClass());
		List<Product> products = productService.findAll();
		products.stream().forEach(p -> logger.info("All Products fetched are = " +p.toString()));
		return products;
	}
	
	@PostMapping("/insert")
	public ResponseEntity<Account> insertRecord(@RequestBody Account account) {
		logger.info("Inside insertRecord() of class" +this.getClass());
		Account userSaved = null;
	
		switch(account.getBank_name()) {
		case AXIS_BANK:
			Axis axis = new Axis();
			axis.setBalance(account.getBalance());
			axis.setBank_name(account.getBank_name());
			axis.setKyc(account.getKyc());
			axis.setName(account.getName());
			axis.setNumber(account.getNumber());
			axisRepo.save(axis);
			logger.info("Record " +axis.toString()+ " is Saved successfully to AXIS Bank");
			break;
		case HDFC_BANK:
			Hdfc hdfc = new Hdfc();
			hdfc.setBalance(account.getBalance());
			hdfc.setBank_name(account.getBank_name());
			hdfc.setKyc(account.getKyc());
			hdfc.setName(account.getName());
			hdfc.setNumber(account.getNumber());
			hdfcRepo.save(hdfc);
			logger.info("Record " +hdfc.toString()+ " is Saved successfully to HDFC Bank");
			break;
		case ICICI_BANK:
			Icici icici = new Icici();
			icici.setBalance(account.getBalance());
			icici.setBank_name(account.getBank_name());
			icici.setKyc(account.getKyc());
			icici.setName(account.getName());
			icici.setNumber(account.getNumber());
			iciciRepo.save(icici);
			logger.info("Record " +icici.toString()+ " is Saved successfully to AXIS Bank");
			break;
		}
		
		if(account.getNumber() != null) {
			userSaved = accountServices.save(account);
			logger.info("Account saved successfully in Database " +userSaved.toString());
		}else {
			logger.info("Since Account Number is not generated successfully, not creating the User please connect with Bank");
			}
		return ResponseEntity.ok(userSaved);
	}
	
	@GetMapping("/allUsers")
	public Iterable<Account> getAllUsers(){
		logger.info("Inside getAllUsers() method");
		return accountServices.getAllUsers();
	}
	
	@GetMapping("/allNonKYCUsers")
	public Account getAllNonKYCUsers() {
		logger.info("Inside getAllNonKYCUsers() method");
		return accountServices.getAllNonKYCUsers();
	}
	
	@PostMapping("/login")
	public Account getLoginStatus(@RequestBody Credentials cred) {
		logger.info("Inside getLoginStatus() method");
		Account account = null;
		Credentials credentials = loginServices.getUsernameAndPassword(cred.getUsername());
		if(credentials != null) {
			String name = credentials.getName();
			if(!StringUtils.isEmpty(name)) {
				account = accountServices.getAmount(name);
			}
			if(cred.getUsername().equals(credentials.getUsername()) && cred.getPassword().equals(credentials.getPassword())){
				logger.info("Credentials are validated successfully for Account " +credentials.getAccount_number()+ " and hence returing value");
				return account;
			}
		}
		return null;
	}
	
	
	@PostMapping("/transaction")
	@Transactional(rollbackFor = Exception.class)
	public Banks doTransaction(@RequestBody Transaction trans) {
		logger.info("Inside doTransaction() method");
		
		Banks creditorBalance = accountServices.getCreditorBalance(trans.getReceiverAccountNumber(), trans);
		Banks debitorBalance = accountServices.getDebitorBalance(trans.getSenderAccountNumber(), trans);
		
		switch(trans.getBank_name()) {
		case AXIS_BANK:
			if(Double.valueOf(trans.getSenderBalance()) > Double.valueOf(trans.getReceiverAmount())) {
				Axis axisCreditorBalance = (Axis)creditorBalance;
				Axis axisDebitorBalance = (Axis)debitorBalance;
				double axisCreditorAmount = axisCreditorBalance.getBalance() + Double.valueOf(trans.getReceiverAmount());
				double axisDebitorAmount = axisDebitorBalance.getBalance() - Double.valueOf(trans.getReceiverAmount());
				axisCreditorBalance.setBalance(axisCreditorAmount);
				axisDebitorBalance.setBalance(axisDebitorAmount);
				return accountServices.saveAxisTransaction(axisCreditorBalance, axisDebitorBalance);
			}else {
				logger.error("The Debitor doesn't have sufficeint funds to Transfer Amount, please connect with Bank Support Team");
				throw new ResourceNotFoundException("The Debitor doesn't have sufficeint funds to Transfer Amount, please connect with Bank Support Team");
			}
		case HDFC_BANK:
			if(Double.valueOf(trans.getSenderBalance()) > Double.valueOf(trans.getReceiverAmount())) {
				Hdfc hdfcCreditorBalance = (Hdfc)creditorBalance;
				Hdfc hdfcDebitorBalance = (Hdfc)debitorBalance;
				double hdfcCreditorAmount = hdfcCreditorBalance.getBalance() + Double.valueOf(trans.getReceiverAmount());
				double hdfcfinalDebitorAmount = hdfcDebitorBalance.getBalance() - Double.valueOf(trans.getReceiverAmount());
				hdfcCreditorBalance.setBalance(hdfcCreditorAmount);
				hdfcDebitorBalance.setBalance(hdfcfinalDebitorAmount);
				return accountServices.saveHdfcTransaction(hdfcCreditorBalance, hdfcDebitorBalance);
		}else {
			logger.error("The Debitor doesn't have sufficeint funds to Transfer Amount, please connect with Bank Support Team");
			throw new ResourceNotFoundException("The Debitor doesn't have sufficeint funds to Transfer Amount, please connect with Bank Support Team");
		}
		case ICICI_BANK:
			if(Double.valueOf(trans.getSenderBalance()) > Double.valueOf(trans.getReceiverAmount())) {
				Icici iciciCreditorBalance = (Icici)creditorBalance;
				Icici iciciDebitorBalance = (Icici)debitorBalance;
				double iciciCreditorAmount = iciciCreditorBalance.getBalance() + Double.valueOf(trans.getReceiverAmount());
				double icicifinalDebitorAmount = iciciDebitorBalance.getBalance() - Double.valueOf(trans.getReceiverAmount());
				iciciCreditorBalance.setBalance(iciciCreditorAmount);
				iciciDebitorBalance.setBalance(icicifinalDebitorAmount);
				return accountServices.saveIciciTransaction(iciciCreditorBalance, iciciDebitorBalance);
			}else {
				logger.error("The Debitor doesn't have sufficeint funds to Transfer Amount, please connect with Bank Support Team");
				throw new ResourceNotFoundException("The Debitor doesn't have sufficient funds to Transfer,please connect with Bank Support Team");
			}
		}
		return null;
}
	
	
}
