package sso.server.validate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import sso.client.domain.User;
import sso.server.login.TokenUserManager;

@WebServlet("/validate")
public class TokenValidateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1296067069565278294L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{

		User user = TokenUserManager.validateToken(req.getParameter("token"));
		if (user == null) {
			res.getWriter().write("");
		} else {
			Gson gson = new Gson();
			res.getWriter().write(gson.toJson(user));
		}
	}
}
