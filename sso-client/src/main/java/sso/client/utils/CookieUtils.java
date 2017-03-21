package sso.client.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	
	public static final String TOKEN ="token";

	public static String getCookieValue(HttpServletRequest req, String cookieName) {
		String value = null;
		Cookie[] cookies = req.getCookies();
		if (cookies == null || cookies.length==0) {
			return null;
		}
		for (Cookie cookie :cookies){
			if (cookieName.equals(cookie.getName())) {
				value = cookie.getValue();
				break;
			}
		}
		return value;
	}
	
	public static void addCookie(HttpServletResponse res, String token) {
		Cookie tokenCookie = new Cookie("token", token);
		tokenCookie.setPath("/");
		//tokenCookie.setDomain(DOMAIN);
		tokenCookie.setHttpOnly(true);
		res.addCookie(tokenCookie);
	}
}
