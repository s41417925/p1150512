package com.example.p1150512.dto.request;

import com.example.p1150512.constants.ValidationMsg;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdatePasswordReq {

	@NotBlank(message = ValidationMsg.INVALID_ACCOUNT_FORMAT)
	@Pattern(regexp = ValidationMsg.ACCOUNT_REGEX, message = ValidationMsg.INVALID_ACCOUNT_FORMAT)
	private String account;
	@NotBlank(message = ValidationMsg.INVALID_PASSWORD_FORMAT)
   @Pattern(regexp = ValidationMsg.PASSWORD_REGEX, message = ValidationMsg.INVALID_PASSWORD_FORMAT)
	private String oldPassword;
	@NotBlank(message = ValidationMsg.INVALID_PASSWORD_FORMAT)
   @Pattern(regexp = ValidationMsg.PASSWORD_REGEX, message = ValidationMsg.INVALID_PASSWORD_FORMAT)
	private String newPassword;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}