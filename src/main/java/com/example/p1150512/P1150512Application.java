package com.example.p1150512;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// 以下是 spring boot 4.0.0 版本以上的排除方式:
// @SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class
//ServletWebSecurityAutoConfiguration.class })
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)


public class P1150512Application {

	public static void main(String[] args) {
		SpringApplication.run(P1150512Application.class, args);
	}

}
