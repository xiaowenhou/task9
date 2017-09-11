package com.putaoteng.task8.utils.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class AutoAuthenticate {
	public static void cookieAuthenticate(HttpServletResponse response, String username,
                                          String loginTime) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// 先将用户名和登陆时间进行加密
		String encryCookieValue1 = MD5Encryption.EncoderByMd5(username);
		String encryCookieValue2 = MD5Encryption.EncoderByMd5("" + loginTime);
		// 然后再将用户名和登陆时间再按照某种规则进行加密(这里也可以使用其他的加密手段)
		String encryCookieValue3 = MD5Encryption.EncoderByMd5("[" + encryCookieValue1 + "$" + "]" + encryCookieValue2);

		//生成token
		String token = JsonWebToken.createJWT(encryCookieValue1, encryCookieValue2, encryCookieValue3, 3600 * 1);
		// 将令牌放入cookie中
		CookieUtils.addCookie(response, "token", token, null);
	}
	
	public static void sessionAuthenticate(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("isLogin", "true");
		session.setMaxInactiveInterval(60);
	}
}
