package com.company.project.VO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthLogInUserVO {

	@NotNull(message = "login.emailOrUsername.null")
	@Size(min = 4, max = 40, message = "login.username.length")
	private String emailOrUsername;

	@NotNull(message = "login.password.null")
	@Size(min = 4, max = 40, message = "login.password.length")
	private String password;

	public AuthLogInUserVO() {

	}

	public String getEmailOrUsername() {
		return emailOrUsername;
	}

	public void setEmailOrUsername(String emailOrUsername) {
		this.emailOrUsername = emailOrUsername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
