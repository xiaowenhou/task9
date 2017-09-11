package com.putaoteng.task8.utils.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Clock;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

public class JsonWebToken {
	private static Logger logger = Logger.getLogger(JsonWebToken.class.getName());

	public static String createJWT(String string1, String string2, String string3, int date) {
		String token = "";

		// 设置过期时间
		Calendar calendar = Calendar.getInstance();
		if (date >= 0) {
			calendar.add(Calendar.SECOND, date);
		} else {
			calendar.add(Calendar.SECOND, 30 * 24 * 3600);
		}
		Date exp = calendar.getTime();

		try {
			//signature部分 采用HMAC256算法加密,密钥为task7
			Algorithm algorithmHS = Algorithm.HMAC256("task7");
			//生成token
			token = JWT.create().withIssuer("xiaowenhou")
					.withSubject("Authenticate")
					.withClaim("username", string1)
					.withClaim("userLoginAt", string2)
					.withClaim("encry", string3)
					.withExpiresAt(exp)
					.sign(algorithmHS);
		} catch (IllegalArgumentException e) {
			logger.error("createJWT() failed, 参数错误,生成密钥失败: " + e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.error("createJWT() failed, 不支持的编码异常,生成密钥失败: " + e.getMessage());
			e.printStackTrace();
		}

		return token;
	}

	//验证token
	public static boolean parseJWT(String token) throws NoSuchAlgorithmException {
		try {
			Algorithm algorithmHS = Algorithm.HMAC256("task7");

			JWTVerifier.BaseVerification verification = (JWTVerifier.BaseVerification) JWT.require(algorithmHS);

			Clock clock = new Clock() {
				@Override
				public Date getToday() {
					return new Date();
				}
			};
			JWTVerifier verifier = verification.build(clock);
			DecodedJWT jwt = verifier.verify(token);

			String username = jwt.getClaim("username").asString();
			String userLoginAt = jwt.getClaim("userLoginAt").asString();
			String encry = jwt.getClaim("encry").asString();

			if (encry.equals(MD5Encryption.EncoderByMd5("[" + username + "$" + "]" + userLoginAt))) {
				return true;
			} else {
				return false;
			}

		} catch (IllegalArgumentException e) {
			logger.error("parseJWT(), 参数错误,生成密钥失败: " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("parseJWT(), 不支持的编码异常,生成密钥失败: " + e.getMessage());
		} catch (TokenExpiredException e){
			logger.error("parseJWT(), token令牌过期!");
			return false;
		}
		return false;
	}

}
