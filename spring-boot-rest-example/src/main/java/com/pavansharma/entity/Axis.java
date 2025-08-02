package com.pavansharma.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.pavansharma.interfaces.Banks;

import javax.persistence.Id;


@Entity
@Table(name = "axis_bank", schema = "pavan")
public class Axis implements Banks{

	@Id
	@Column(name = "account_number", unique = true)
	private String number;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "balance")
	private double balance;
	
	@Column(name = "kyc")
	private String kyc;
	
	@Column(name = "bank_name")
	private String bank_name;
	
	
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getKyc() {
		return kyc;
	}

	public void setKyc(String kyc) {
		this.kyc = kyc;
	}
	
	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	@Override
	public String toString() {
		return "Account [number=" + number + ", name=" + name + ", balance=" + balance + ", kyc=" + kyc + ", bank_name="
				+ bank_name + "]";
	}
	
	
	
}
