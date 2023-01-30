package com.xueyu.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author durance
 */
@SpringBootApplication
@MapperScan("com.xueyu.user.mapper")
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class);
	}

}
