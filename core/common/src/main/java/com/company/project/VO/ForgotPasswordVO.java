package com.company.project.VO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ForgotPasswordVO {

	@NotNull(message = "forgot.emailOrUsername.null")
	@Size(min = 4, max = 40, message = "forgot.emailOrUsername.length")
	private String emailOrUsername;

}
