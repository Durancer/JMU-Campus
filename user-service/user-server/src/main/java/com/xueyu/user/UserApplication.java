package com.xueyu.user;

import com.xueyu.common.ampq.annotation.EnableAmqpMessageConverterConfig;
import com.xueyu.common.data.annotation.EnableMybatisPlusIPage;
import com.xueyu.common.data.annotation.EnableRedisSerialize;
import com.xueyu.common.web.annotation.EnableDefaultExceptionAdvice;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author durance
 */
@SpringBootApplication
@MapperScan("com.xueyu.user.mapper")
@EnableMybatisPlusIPage
@EnableAmqpMessageConverterConfig
@EnableFeignClients(basePackages = {"com.xueyu.resource.client"})
@EnableRedisSerialize
@EnableScheduling
@EnableDefaultExceptionAdvice
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class);
	}

}
