package com.example.p1150512.dto.request;

import com.example.p1150512.constants.ValidationMsg;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AtmTransactionReq {
	@NotBlank(message = ValidationMsg.INVALID_ACCOUNT_FORMAT)
	@Pattern(regexp = ValidationMsg.ACCOUNT_REGEX, message = ValidationMsg.INVALID_ACCOUNT_FORMAT)
	private String account;
	@NotBlank(message = ValidationMsg.INVALID_PASSWORD_FORMAT)
	@Pattern(regexp = ValidationMsg.PASSWORD_REGEX, message = ValidationMsg.INVALID_PASSWORD_FORMAT)
	private String password;
	// 存款/提款金額 (查詢餘額時可不傳)
	private int amount;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
