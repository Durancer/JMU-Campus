package com.xueyu.post;

import com.xueyu.common.data.annotation.EnableMybatisPlusIPage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 帖子服务
 *
 * @author durance
 */
@SpringBootApplication
@EnableMybatisPlusIPage
@MapperScan("com.xueyu.post.mapper")
public class PostApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostApplication.class);
	}

}
