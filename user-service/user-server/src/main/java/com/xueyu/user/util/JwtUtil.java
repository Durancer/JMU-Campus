package com.xueyu.user.util;

import com.xueyu.user.config.JwtProperties;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT 生成工具类
 *
 * @author durance
 */
public class JwtUtil {

	/**
	 * 签发 jwt
	 *
	 * @param id        令牌id
	 * @param subject   主题
	 * @param ttlMillis jwt有效期
	 * @return jwt
	 */
	public static String createJwt(String id, String subject, Long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		if (ttlMillis == null) {
			// 如果没有设置时间，设置默认过期时间：1天
			ttlMillis = JwtProperties.expiration;
		}
		long expMillis = nowMillis + ttlMillis;
		Date expDate = new Date(expMillis);
		SecretKey secretKey = generalKey();
		JwtBuilder builder = Jwts.builder()
				.setId(id)
				// 主题  可以是JSON数据
				.setSubject(subject)
				// 签发者
				.setIssuer(JwtProperties.issuer)
				// 签发时间
				.setIssuedAt(now)
				//使用HS256对称加密算法签名, 第二个参数为秘钥
				.signWith(signatureAlgorithm, secretKey)
				// 设置过期时间
				.setExpiration(expDate);
		return builder.compact();
	}

	/**
	 * 无参使用默认配置签发jwt
	 *
	 * @return jwt
	 */
	public static String createJwt() {
		return createJwt(UUID.randomUUID().toString(), "xueyukeji", null);
	}

	/**
	 * 生成加密后的秘钥 secretKey
	 *
	 * @return 秘钥
	 */
	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.getDecoder().decode(JwtProperties.key);
		return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	}

}