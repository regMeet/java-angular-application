package com.company.project.VO;

public class AuthEntityVO {
	String token;
	AuthUserVO user;

	public AuthEntityVO(String token, AuthUserVO user) {
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
