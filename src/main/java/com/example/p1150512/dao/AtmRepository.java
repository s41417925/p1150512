package com.example.p1150512.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.p1150512.entity.Atm;

public interface AtmRepository extends JpaRepository<Atm, String> {
	@Modifying
	@Query(value = """
			INSERT IGNORE INTO atm(account, password, balance)
			VALUES (:account, :password, :balance)
			""", nativeQuery = true)
	int addInfo(@Param("account") String account, @Param("password") String password,
			@Param("balance") Integer balance);
}
