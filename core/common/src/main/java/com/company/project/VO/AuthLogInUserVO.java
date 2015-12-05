package com.company.project.VO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AuthLogInUserVO {

	@NotNull(message = "login.emailOrUsername.null")
	@Size(min = 4, max = 40, message = "login.emailOrUsername.length")
	private String emailOrUsername;

	@NotNull(message = "login.password.null")
	@Size(min = 4, max = 40, message = "login.password.length")
	private String password;

}
