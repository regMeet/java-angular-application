package com.company.project.Auth;

public class Token {
	String token;
	String currentUser;

	public Token(String token, String currentUser) {
		this.token = token;
		this.currentUser = currentUser;
	}

	public String getToken() {
		return token;
	}

	public String getCurrentUser() {
		return currentUser;
	}
}