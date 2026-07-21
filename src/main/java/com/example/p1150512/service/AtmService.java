package com.example.p1150512.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.p1150512.dao.AtmRepository;
import com.example.p1150512.dto.response.AccountBalanceRes;
import com.example.p1150512.entity.Atm;
import com.example.p1150512.enums.enums.ReplyMessage;

@Service
public class AtmService {

	private final AtmRepository atmRepository;

	private final PasswordEncoder passwordEncoder;

	public AtmService(AtmRepository atmRepository, PasswordEncoder passwordEncoder) {

		this.atmRepository = atmRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// ==========================
	// 驗證帳號格式
	// ==========================
	private void validateAccount(String account) {

		if (account == null || !account.matches("^[A-Za-z0-9_]{3,8}$")) {

			throw new IllegalArgumentException(ReplyMessage.INVALID_ACCOUNT_FORMAT.getMessage());
		}
	}

	// ==========================
	// 驗證密碼格式
	// ==========================
	private void validatePassword(String password) {

		if (password == null || !password.matches("^[A-Za-z0-9_]{8,16}$")) {

			throw new IllegalArgumentException(ReplyMessage.INVALID_PASSWORD_FORMAT.getMessage());
		}
	}

	// ==========================
	// 驗證帳號+密碼
	// ==========================
	private Atm checkAccountAndPassword(String account, String password) {

		validateAccount(account);

		validatePassword(password);

		Atm atm = atmRepository.findById(account).orElseThrow(() ->

		new IllegalArgumentException(ReplyMessage.ACCOUNT_NOT_FOUND.getMessage()));

		// BCrypt驗證
		if (!passwordEncoder.matches(password, atm.getPassword())) {

			throw new IllegalArgumentException(ReplyMessage.INVALID_PASSWORD.getMessage());
		}

		return atm;
	}

	// ==========================
	// 註冊帳號
	// ==========================
	@Transactional
	public void addInfo(String account, String password) {

		// 檢查格式
		validateAccount(account);

		validatePassword(password);

		// 檢查帳號是否存在
		if (atmRepository.existsById(account)) {

			throw new IllegalArgumentException(ReplyMessage.DUPLICATE_ACCOUNT.getMessage());
		}

		Atm atm = new Atm();

		atm.setAccount(account);

		// 密碼加密
		atm.setPassword(passwordEncoder.encode(password));

		// 初始餘額
		atm.setBalance(0);

		atmRepository.save(atm);

	}

	// ==========================
	// 查詢餘額
	// ==========================
	@Transactional(readOnly = true)
	public ResponseEntity<AccountBalanceRes> getBalanceByAccount(String account, String password) {
		Atm atm = checkAccountAndPassword(account, password);
		AccountBalanceRes response = new AccountBalanceRes(atm.getAccount(), atm.getBalance());

		return ResponseEntity.ok(response);

	}

	// ==========================
	// 修改密碼
	// ==========================
	@Transactional
	public void updatePasswordByAccount(String account, String oldPassword, String newPassword) {

		Atm atm = checkAccountAndPassword(account, oldPassword);

		validatePassword(newPassword);

		atm.setPassword(passwordEncoder.encode(newPassword));

	}

	// ==========================
	// 存款
	// ==========================
	@Transactional
	public int deposit(String account, String password, Integer amount) {

		if (amount == null || amount <= 0) {

			throw new IllegalArgumentException(ReplyMessage.INVALID_AMOUNT.getMessage());

		}

		Atm atm = checkAccountAndPassword(account, password);

		int newBalance = atm.getBalance() + amount;

		atm.setBalance(newBalance);

		return newBalance;

	}

	// 少了提款功能
}
