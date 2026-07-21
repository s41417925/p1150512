package com.example.p1150512.enums;

public class enums {
	public enum ReplyMessage {
		/* 列舉的項目，用逗號區隔多個，結尾符號(;)只會有一個 */
		ATM_IS_NULL("ATM is null!!"), //
		INVALID_ACCOUNT_FORMAT("Invalid account format!!"), //
		INVALID_PASSWORD_FORMAT("Invalid password format!!"), //
		INVALID_BALANCE_AMOUNT("Invalid balance amount!!"), //
		ACCOUNT_CREATED_SUCCESSFULLY("Account created successfully!!"), //
		DUPLICATE_ACCOUNT("Duplicated account!!"), //
		ACCOUNT_NOT_FOUND("Account not found!!"), //
		INVALID_PASSWORD("Invalid password!!"), //
		INVALID_AMOUNT("Amount must be greater than zero!!"), //
		INSUFFICIENT_BALANCE("Insufficient balance!!"), //
		PASSWORD_UPDATED_SUCCESSFULLY("Password updated successfully!!"), //
		DEPOSIT_SUCCESSFUL("Deposit successful!!"), //
		WITHDRAWAL_SUCCESSFUL("Withdrawal successful!!");

		private String message;

		// 建構方法: enum 沒有預設建構方法，所以無法被 new
		private ReplyMessage(String message) {
			this.message = message;
		}

		// get/set 方法
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

}
