package com.xueyu.resource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author durance
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

	/**
	 * 连接地址
	 */
	private String url;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

}
