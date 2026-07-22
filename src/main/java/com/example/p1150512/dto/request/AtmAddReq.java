package com.example.p1150512.dto.request;

import com.example.p1150512.constants.ValidationMsg;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AtmAddReq {
	@NotBlank(message = ValidationMsg.INVALID_ACCOUNT_FORMAT)
	@Pattern(regexp = ValidationMsg.ACCOUNT_REGEX, message = ValidationMsg.INVALID_ACCOUNT_FORMAT)
	private String account;
	@NotBlank(message = ValidationMsg.INVALID_PASSWORD_FORMAT)
	@Pattern(regexp = ValidationMsg.PASSWORD_REGEX, message = ValidationMsg.INVALID_PASSWORD_FORMAT)
	private String password;
	/* 至少是 0 */
	@Min(value = 0, message = ValidationMsg.INVALID_BALANCE_AMOUNT)
	private int balance;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	

}
