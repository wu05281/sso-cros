package sso.client.filter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import sso.client.domain.User;
import sso.client.utils.CookieUtils;

public class SSOFilter implements Filter {

	// SSO Server后台认证接口
	private static final String SSO_VALIDATE_URL = "http://auth.com:8080/server/validate";

	// SSO Server登录认证页面URL
	private static final String SSO_AUTH_URL = "http://auth.com:8080/server/auth";

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse) response;

		StringBuffer orignUrl = req.getRequestURL();
		//原始请求连接，登录成功后回跳
		String queryString = req.getQueryString();
		if (queryString != null && !"".equals(queryString)) {
			orignUrl.append(queryString);
		}

		//取cookie，如果没有，则取请求参数是否有有效token信息
		String token = CookieUtils.getCookieValue(req, CookieUtils.TOKEN);
		if (token == null) {
			token = req.getParameter("token");
		}

		if (token == null) {
			//去验证服务器验证（此处应附带验证必要信息，已确保身份验证，例如预先约定的给对应站点的AppToken）
			//服务端验证不合法，至错误页面；验证无登录信息，跳转至验证服务的登录页
			//登录成功之，生产授权访问token，保存至服务器，并随原始访问连接，跳转至原请求资源地址。
			res.sendRedirect(SSO_AUTH_URL + "?origUrl=" + URLEncoder.encode(orignUrl.toString(), "utf-8"));
		} else {
			//后端接口验证token合法性，并获取用户信息；放入session
			User user = validate(token);
			if (user == null) {
				res.sendRedirect(SSO_AUTH_URL + "?origUrl=" + URLEncoder.encode(orignUrl.toString(), "utf-8"));
			} else {
				CookieUtils.addCookie(res, token);
				chain.doFilter(req, res);
			}
		}
	}


	private User validate(String token) throws IOException, ServletException{
		//调用验证接口
		InputStream in = null;
		User user = null;
		try{
			URL validateUrl = new URL(SSO_VALIDATE_URL+"?token=" +token);
			HttpURLConnection conn = (HttpURLConnection) validateUrl.openConnection();
			conn.connect();
			in = conn.getInputStream();
			byte[] buffer = new byte[in.available()];
			in.read(buffer);
			String ret = new String(buffer);
			//token验证失败
			if (ret.length() != 0) {
				Gson gs = new Gson();
				user = gs.fromJson(ret, User.class);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			in.close();
		}
		return user;
	}
	public void init(FilterConfig arg0) throws ServletException {

	}



}
