package com.pavansharma.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account", schema = "pavan")
public class Transaction {

	@Id
	@Column(name = "account_number")
	private String receiverAccountNumber;
	
	@Column(name = "balance")
	private String senderBalance;
	
	private String receiverAmount;
	
	private String senderAccountNumber;
	
	private String bank_name;

	public String getSenderBalance() {
		return senderBalance;
	}

	public void setSenderBalance(String senderBalance) {
		this.senderBalance = senderBalance;
	}

	public String getReceiverAccountNumber() {
		return receiverAccountNumber;
	}

	public void setReceiverAccountNumber(String receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}

	public String getReceiverAmount() {
		return receiverAmount;
	}

	public void setReceiverAmount(String receiverAmount) {
		this.receiverAmount = receiverAmount;
	}

	public String getSenderAccountNumber() {
		return senderAccountNumber;
	}

	public void setSenderAccountNumber(String senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	
	
	
}
