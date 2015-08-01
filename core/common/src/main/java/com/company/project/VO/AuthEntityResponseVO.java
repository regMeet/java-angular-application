package com.company.project.VO;

public class AuthEntityResponseVO {
	String token;
	AuthUserVO user;

	public AuthEntityResponseVO(String token, AuthUserVO user) {
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public AuthUserVO getUser() {
		return user;
	}

}
