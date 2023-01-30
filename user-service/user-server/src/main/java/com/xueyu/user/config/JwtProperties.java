package com.xueyu.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author durance
 */
@Component
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

	/**
	 * jwt密钥
	 */
	String key;

	/**
	 * 签发者
	 */
	String issuer;

	/**
	 * 过期时间，单位：秒
	 */
	Long expiration;

}
