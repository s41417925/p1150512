package com.example.p1150512.constants;

public enum ReplyMsg {
	ACCOUNT_CREATED_SUCCESSFULLY("帳號建立成功"),
	DUPLICATE_ACCOUNT("帳號已存在"),
	PASSWORD_UPDATED_SUCCESSFULLY("密碼修改成功"),
	DEPOSIT_SUCCESSFUL("存款成功"),
	WITHDRAWAL_SUCCESSFUL("提款成功"),
	INVALID_AMOUNT("金額必須大於 0"),
	INSUFFICIENT_BALANCE("餘額不足"),
	ACCOUNT_NOT_FOUND("查無此帳號"),
	INVALID_PASSWORD("密碼錯誤");

	private final String message;

	ReplyMsg(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
