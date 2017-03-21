package sso.server.validate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sso.client.utils.CookieUtils;

@WebServlet("/cros/validate")
public class TokenValidateCrosServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1296067069565278294L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
		/**
		 * cookie对应域名为登录服务域名，故调用登录服务接口获取cookie域名值
		 */
		System.out.println(req.getRequestURL());
		String token = CookieUtils.getCookieValue(req, CookieUtils.TOKEN);
		Cookie[] cs = req.getCookies();
		System.out.println(cs);
		if (token == null) {
			res.getWriter().write("");
		} else {
			res.getWriter().write(token);
		}
	}
}
