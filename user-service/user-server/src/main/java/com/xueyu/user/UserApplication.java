package com.xueyu.user;

import com.xueyu.common.data.annotation.EnableMybatisPlusIPage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author durance
 */
@SpringBootApplication
@MapperScan("com.xueyu.user.mapper")
@EnableMybatisPlusIPage // 启动分页功能
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class);
	}

}
