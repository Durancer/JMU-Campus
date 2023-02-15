package com.xueyu.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 小程序配置项
 *
 * @author durance
 */
@Configuration
@ConfigurationProperties(prefix = "minapp")
public class MinAppProperties {

	/**
	 * 小程序appId
	 */
	public static String appId;

	/**
	 * 小程序密钥
	 */
	public static String secret;

	public static void setAppId(String appId) {
		MinAppProperties.appId = appId;
	}

	public static void setSecret(String secret) {
		MinAppProperties.secret = secret;
	}

}
