package com.xueyu.user;

import com.xueyu.common.ampq.annotation.EnableAmqpMessageConverterConfig;
import com.xueyu.common.data.annotation.EnableMybatisPlusIPage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author durance
 */
@SpringBootApplication
@MapperScan("com.xueyu.user.mapper")
@EnableMybatisPlusIPage
@EnableAmqpMessageConverterConfig
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class);
	}

}
