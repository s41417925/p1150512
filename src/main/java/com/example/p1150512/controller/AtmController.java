package com.example.p1150512.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.p1150512.service.AtmService;
import com.example.p1150512.service.PersonInfoService;

@RestController
@RequestMapping("/atm")
public class AtmController {
	@Autowired
	private AtmService atmService;

	@PostMapping("/register")
	// ResponseEntity會回傳狀態碼
	public ResponseEntity<String> register(@RequestParam("account") String account,
			@RequestParam("password") String password) {
		atmService.addInfo(account, password);
		return ResponseEntity.ok("註冊成功");
	}

	@GetMapping("/balance")
	public ResponseEntity<Integer> getBalance(@RequestParam("account") String account,
			@RequestParam("password") String password) {
		Integer balance = atmService.getBalanceByAccount(account, password);
		return ResponseEntity.ok(balance);
	}

	@PostMapping("/deposit")
	public ResponseEntity<String> deposit(@RequestParam String account, @RequestParam String password,
			@RequestParam Integer amount) {

		int newBalance = atmService.deposit(account, password, amount);

		return ResponseEntity.ok("存款成功，目前餘額：" + newBalance);
	}

	@PostMapping("/updatePasswordByAccount")
	public ResponseEntity<String> updatePasswordByAccount(@RequestParam("account") String account,
			@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
		atmService.updatePasswordByAccount(account, oldPassword, newPassword);
		return ResponseEntity.ok("密碼變更完成");

	}

}
