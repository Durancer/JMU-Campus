package com.xueyu.resource;

import com.xueyu.common.web.annotation.EnableDefaultExceptionAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author durance
 */
@SpringBootApplication
@EnableDefaultExceptionAdvice
public class ResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceApplication.class);
	}

}
