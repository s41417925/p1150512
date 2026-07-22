package com.example.p1150512.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.p1150512.dto.request.AtmAddReq;
import com.example.p1150512.dto.request.AtmTransactionReq;
import com.example.p1150512.dto.request.DepositReq;
import com.example.p1150512.dto.request.UpdatePasswordReq;
import com.example.p1150512.entity.Atm;
import com.example.p1150512.service.AtmService;
import com.example.p1150512.service.AtmService1;
import com.example.p1150512.service.PersonInfoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/atm")
public class AtmController {
	@Autowired
	private AtmService atmService;
	/* 在參數前加上 @Valid，才會讓 AtmAddReq 裡面的 Validation 生效*/
	@PostMapping("/add-info")
	public ResponseEntity<String> addInfo(@Valid @RequestBody AtmAddReq req) {
		try {
			return atmService.addInfo(req);
		} catch (Exception e) {
			// 捕捉 Service 丟出的 RuntimeException (回滾異常)
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	// 1. 查詢餘額 (帶入 JSON)
	@PostMapping("/get-balance")
	public ResponseEntity<?> getBalance(@Valid @RequestBody AtmTransactionReq req) {
		try {
			return atmService.getBalanceByAccount(req);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	// 2. 修改密碼 (帶入 JSON)
	@PostMapping("/update-password")
	public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordReq req) {
		try {
			return atmService.updatePasswordByAccount(req);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	// 3. 存款 (帶入 JSON)
	@PostMapping("/deposit")
	public ResponseEntity<String> deposit(@Valid @RequestBody AtmTransactionReq req) {
		try {
			return atmService.deposit(req);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	// 4. 提款 (帶入 JSON)
	@PostMapping("/withdraw")
	public ResponseEntity<String> withdraw(@Valid @RequestBody AtmTransactionReq req) {
		try {
			return atmService.withdraw(req);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
}
