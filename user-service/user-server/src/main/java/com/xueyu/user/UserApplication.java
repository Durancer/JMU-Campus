package com.xueyu.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author durance
 */
@SpringBootApplication
@MapperScan("com.xueyu.user.mapper")
public class UserApplication {

}
