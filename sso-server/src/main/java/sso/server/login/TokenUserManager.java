package sso.server.login;

import java.util.HashMap;
import java.util.Map;

import sso.client.domain.User;

public class TokenUserManager {
	
	private static Map<String, User> tokens = new HashMap<String, User>();
	
	public static void addToken(String token, User user) {
		tokens.put(token, user);
	}
	
	public static User validateToken(String token) {
		return tokens.get(token);
	}

}
