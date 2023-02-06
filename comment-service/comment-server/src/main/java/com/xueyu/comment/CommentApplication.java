package com.xueyu.comment;

import com.xueyu.common.ampq.annotation.EnableAmqpMessageConverterConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 留言评论服务
 *
 * @author durance
 */
@SpringBootApplication
@MapperScan("com.xueyu.comment.mapper")
@EnableAmqpMessageConverterConfig
@EnableFeignClients(basePackages = {"com.xueyu.user.client"})
public class CommentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentApplication.class);
	}

}
