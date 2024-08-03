package com.xueyu.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author durance
 */
@Configuration
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

	/**
	 * 免鉴权的接口
	 */
	public static Set<String> matchers;

	/**
	 * 管理员接口
	 */
	public static Set<String> adminMatchers;

	public void setKey(String key) {
		JwtProperties.key = key;
	}

	public void setIssuer(String issuer) {
		JwtProperties.issuer = issuer;
	}

	public void setExpiration(Long expiration) {
		JwtProperties.expiration = expiration;
	}

	public void setMatchers(Set<String> matchers) {
		JwtProperties.matchers = matchers;
	}

	public void setAdminMatchers(Set<String> adminMatchers) {
		JwtProperties.adminMatchers = adminMatchers;
	}

}
