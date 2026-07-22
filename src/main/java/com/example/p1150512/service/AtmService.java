package com.example.p1150512.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.p1150512.dto.request.AtmAddReq;
import com.example.p1150512.dto.request.AtmTransactionReq;
import com.example.p1150512.dto.request.UpdatePasswordReq;
import com.example.p1150512.dto.response.AccountBalanceRes;
import com.example.p1150512.entity.Atm;

@Service
public class AtmService {
	// 密碼加密器
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	@Autowired
	private AtmDao atmDao;

	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> addInfo(AtmAddReq req) {
		String account = req.getAccount();
		String password = req.getPassword();
		int balance = req.getBalance();
		try {
			// 用加密器將密碼從明文變成密文
			int rowsAffected = atmDao.insertIgnoreNative(account, encoder.encode(password), balance);
			if (rowsAffected > 0) {
				String str = String.format(ReplyMsg.ACCOUNT_CREATED_SUCCESSFULLY.getMessage()//
						+ " 帳號: %s, 初始餘額: %d", account, balance);
				return ResponseEntity.ok(str);
			} else {
				// 如果回傳 0，代表帳號 (主鍵) 已存在而被 MySQL 直接 IGNORE 掉了
				return ResponseEntity.badRequest().body(ReplyMsg.DUPLICATE_ACCOUNT.getMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// ===== 1. 透過帳號取得餘額 (getBalanceByAccount) =====
	// readOnly = true，唯讀
	@Transactional(readOnly = true)
	public ResponseEntity<?> getBalanceByAccount(AtmTransactionReq req) {
		// 驗證身份
		AuthResult auth = authenticate(req.getAccount(), req.getPassword());
		if (!auth.isSuccess()) {
			return ResponseEntity.badRequest().body(auth.getErrorMsg().getMessage());
		}
		// 拿到驗證成功的 Atm 物件
		Atm atm = auth.getAtm();
		// 回傳不含 password 的 DTO 物件
		AccountBalanceRes response = new AccountBalanceRes(atm.getAccount(), atm.getBalance());
		return ResponseEntity.ok(response);
	}

	// ===== 2. 修改密碼 (updatePasswordByAccount) =====
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> updatePasswordByAccount(UpdatePasswordReq req) {
		// 驗證舊身份
		AuthResult auth = authenticate(req.getAccount(), req.getOldPassword());
		if (!auth.isSuccess()) {
			return ResponseEntity.badRequest().body(auth.getErrorMsg().getMessage());
		}
		try {
			// 加密新密碼並更新
			atmDao.updatePassword(req.getAccount(), encoder.encode(req.getNewPassword()));
			return ResponseEntity.ok(ReplyMsg.PASSWORD_UPDATED_SUCCESSFULLY.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// ===== 3. 存款 (deposit) =====
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> deposit(AtmTransactionReq req) {
		// 防呆：存款金額必須大於 0
		if (req.getAmount() <= 0) {
			return ResponseEntity.badRequest().body(ReplyMsg.INVALID_AMOUNT.getMessage());
		}
		// 驗證身份
		AuthResult auth = authenticate(req.getAccount(), req.getPassword());
		if (!auth.isSuccess()) {
			return ResponseEntity.badRequest().body(auth.getErrorMsg().getMessage());
		}
		// 拿到驗證成功的 Atm 物件
		Atm atm = auth.getAtm();
		try {
			int newBalance = atm.getBalance() + req.getAmount();
			atmDao.updateBalance(req.getAccount(), newBalance);
			String message = String.format("%s 帳號: %s, 存款金額: %d, 存款後餘額: %d", ReplyMsg.DEPOSIT_SUCCESSFUL.getMessage(),
					req.getAccount(), req.getAmount(), newBalance);
			return ResponseEntity.ok(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// ===== 4. 提款 (withdraw) =====
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> withdraw(AtmTransactionReq req) {
		// 防呆 1：提款金額必須大於 0
		if (req.getAmount() <= 0) {
			return ResponseEntity.badRequest().body(ReplyMsg.INVALID_AMOUNT.getMessage());
		}
		// 驗證身份
		AuthResult auth = authenticate(req.getAccount(), req.getPassword());
		if (!auth.isSuccess()) {
			return ResponseEntity.badRequest().body(auth.getErrorMsg().getMessage());
		}
		// 拿到驗證成功的 Atm 物件
		Atm atm = auth.getAtm();
		// 防呆 2：檢查餘額是否足夠
		if (atm.getBalance() < req.getAmount()) {
			return ResponseEntity.badRequest().body(ReplyMsg.INSUFFICIENT_BALANCE.getMessage());
		}
		try {
			int newBalance = atm.getBalance() - req.getAmount();
			atmDao.updateBalance(req.getAccount(), newBalance);
			String message = String.format("%s 帳號: %s, 提款金額: %d, 提款後餘額: %d",
					ReplyMsg.WITHDRAWAL_SUCCESSFUL.getMessage(), req.getAccount(), req.getAmount(), newBalance);
			return ResponseEntity.ok(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// ==================== 私有輔助方法 (Helper Methods) ====================
	/**
	 * 帳號密碼格式檢查與 BCrypt 身份比對 驗證通過回傳 Atm 物件，失敗回傳 null
	 */
	private AuthResult authenticate(String account, String rawPassword) {
		// 1. 檢查帳號是否存在 (只查一次 DB)
		Optional<Atm> atmOpt = atmDao.findById(account);
		if (atmOpt.isEmpty()) {
			return new AuthResult(ReplyMsg.ACCOUNT_NOT_FOUND);
		}
		Atm atm = atmOpt.get();
		// 2. 比對密碼
		if (!encoder.matches(rawPassword, atm.getPassword())) {
			return new AuthResult(ReplyMsg.INVALID_PASSWORD);
		}
		// 驗證全部通過，回傳 Atm 物件
		return new AuthResult(atm);
	}

	// =======================
	/**
	 * 驗證結果載體 (AuthResult)
	 */
	private static class AuthResult {

		private final Atm atm;

		private final ReplyMsg errorMsg;

		// 驗證成功的建構子
		public AuthResult(Atm atm) {
			this.atm = atm;
			this.errorMsg = null;
		}

		// 驗證失敗的建構子
		public AuthResult(ReplyMsg errorMsg) {
			this.atm = null;
			this.errorMsg = errorMsg;
		}

		public boolean isSuccess() {
			return atm != null;
		}
		public Atm getAtm() {
			return atm;
		}
		public ReplyMsg getErrorMsg() {
			return errorMsg;
		}
	}
}


}
