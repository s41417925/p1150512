package com.example.p1150512.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.p1150512.entity.Atm;

@Repository
public interface AtmRepository extends JpaRepository<Atm, String> {
	/**
	 * 使用 MySQL 原生 INSERT IGNORE 語法： 若 account 已存在則忽略不寫入，回傳受影響列數: 0 代表重複被忽略，1 代表新增成功
	 */
	@Modifying
	@Query(value = "INSERT IGNORE INTO atm (account, password, balance) VALUES (:account, :password, :balance)", nativeQuery = true)
	public int insertIgnoreNative(//
			@Param("account") String account, //
			@Param("password") String password, //
			@Param("balance") int balance);
	/**
	 * 修改密碼
	 */
	@Modifying
	@Query(value = "UPDATE atm a SET a.password = :newPassword WHERE a.account = :account", nativeQuery = true)
	public int updatePassword(//
			@Param("account") String account, //
			@Param("newPassword") String newPassword);
	/**
	 * 更新餘額 (存款/提款)
	 */
	@Modifying
	@Query(value = "UPDATE atm a SET a.balance = :newBalance WHERE a.account = :account", nativeQuery = true)
	public int updateBalance(//
			@Param("account") String account, //
			@Param("newBalance") int newBalance);
}

