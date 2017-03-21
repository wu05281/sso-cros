package sso.server.validate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sso.client.domain.User;
import sso.client.utils.CookieUtils;
import sso.server.login.TokenUserManager;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1296067069565278294L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{

		String token = CookieUtils.getCookieValue(req, CookieUtils.TOKEN);
		String origUrl = req.getParameter("origUrl");
		req.setAttribute("origUrl", origUrl);
		if (token == null){
			req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
		} else{
			User user = TokenUserManager.validateToken(token);
			if (user == null) {
				req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
			} else {
				res.sendRedirect(origUrl + "?token=" + token);
			}
		}
	}
}
