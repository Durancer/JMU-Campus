package com.xueyu.gateway.util;

import com.xueyu.gateway.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * JWT 效验工具类
 *
 * @author durance
 */
public class JwtUtil {

	/**
	 * 生成加密后的秘钥 secretKey
	 *
	 * @return 秘钥
	 */
	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.getDecoder().decode(JwtProperties.key);
		return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	}

	/**
	 * 解析 jwt，如果解析错误，将抛出异常
	 *
	 * @param jwt token
	 * @return 解析值
	 * @throws Exception 解析异常
	 */
	public static Claims parseJwt(String jwt) throws Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(jwt)
				.getBody();
	}

}
