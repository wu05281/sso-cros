package sso.server.login;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sso.client.domain.User;
import sso.client.utils.CookieUtils;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 730276924663369528L;

	/**
	 * 进入登录页
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("origUrl", req.getParameter("origUrl"));
		req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
		String account = req.getParameter("account");
		String passwd = req.getParameter("passwd");
		String origUrl = req.getParameter("origUrl");
		if ("admin".equals(account) &&"123456".equals(passwd)){
			User user = new User();
			user.setAccount(account);
			user.setId(1);
			user.setName(account);
			String token = generate();
			TokenUserManager.addToken(token, user);
			
			// 3 写cookie JVM 
			CookieUtils.addCookie(res, token);
			
			origUrl = URLDecoder.decode(origUrl, "utf-8");
			res.sendRedirect(origUrl);
		} else {
			req.setAttribute("account", account);
			req.setAttribute("origUrl", origUrl);
			req.setAttribute("errInfo", "用户名或者密码不正确");
			req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
		}
	}
	
	public static String generate() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
}
