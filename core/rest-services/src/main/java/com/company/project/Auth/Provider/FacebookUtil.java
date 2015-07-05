package com.company.project.Auth.Provider;

public class FacebookUtil {
	public final static String ACCESS_TOKEN_URL = "https://graph.facebook.com/v2.3/oauth/access_token";
	public final static String GRAPH_API_URL = "https://graph.facebook.com/v2.3/me";
	private final String secret;

	public FacebookUtil(String secret) {
		this.secret = secret;
	}

	public String getSecret() {
		return secret;
	}

//	public static String getAuthURL(String authCode) {
//		return "https://graph.facebook.com/oauth/access_token?client_id=" + client_id + "&redirect_uri=" + redirect_uri + "&client_secret="
//				+ secret + "&code=" + authCode;
//	}

}
