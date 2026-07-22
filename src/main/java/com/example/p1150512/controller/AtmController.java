package com.example.p1150512.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	@PostMapping("/add-info")
	public ResponseEntity<String> addInfo(@RequestBody Atm atm) {
		try {
			atmService.addInfo(
					atm.getAccount(),
					atm.getPassword()
				 );
			return ResponseEntity.ok("註冊成功");
		} catch (Exception e) {
			// 捕捉 Service 丟出的 RuntimeException (回滾異常)
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 1. 查詢餘額 (帶入 JSON)
	@PostMapping("/get-balance")
	public ResponseEntity<?> getBalance(@RequestBody DepositReq req) {
		try {
			return atmService.getBalanceByAccount(req.getAccount(), req.getPassword());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	// 2. 修改密碼 (帶入 JSON)
	@PostMapping("/update-password")
	public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordReq req) {
		try {
		 atmService.updatePasswordByAccount(req.getAccount(), req.getOldPassword(), req.getNewPassword());
		 return ResponseEntity.ok("密碼修改成功");
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	// 3. 存款 (帶入 JSON)
	@PostMapping("/deposit")
	public ResponseEntity<String> deposit(@RequestBody DepositReq req) {
		try {
			int newBalance = atmService.deposit(req.getAccount(), req.getPassword(), req.getAmount());
			return ResponseEntity.ok("存款成功，目前餘額: " + newBalance);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	// 4. 提款 (帶入 JSON)
	@PostMapping("/withdraw")
	public ResponseEntity<String> withdraw(@RequestBody DepositReq req) {
		try {
			int newBalance = atmService.deposit(req.getAccount(), req.getPassword(), req.getAmount());
			return ResponseEntity.ok("提款成功，目前餘額: " + newBalance);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
}


