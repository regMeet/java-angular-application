package com.company.project.VO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PasswordForgottenVO {

	@NotNull(message = "forgotten.password.null")
	@Size(min = 4, max = 40, message = "forgotten.password.length")
	private String newPassword;

}
