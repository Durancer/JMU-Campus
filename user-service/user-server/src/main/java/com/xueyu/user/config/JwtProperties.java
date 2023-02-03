package com.xueyu.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author durance
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

	/**
	 * jwt密钥
	 */
	public static String key;

	/**
	 * 签发者
	 */
	public static String issuer;

	/**
	 * 过期时间，单位：豪秒
	 */
	public static Long expiration;

}
