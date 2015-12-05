package com.company.project.webservice.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.VO.AuthEntityResponseVO;
import com.company.project.VO.AuthLogInUserVO;
import com.company.project.VO.AuthSignUpUserVO;
import com.company.project.VO.ForgotPasswordVO;
import com.company.project.VO.PasswordForgottenVO;
import com.company.project.VO.SatellizerPayloadVO;
import com.company.project.VO.UserVO;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpContentNotFoundException;
import com.company.project.api.exception.HttpStatusException;
import com.company.project.services.interfaces.AuthService;
import com.company.project.webservice.interfaces.AuthRestService;

@RestController
public class AuthRestServiceImpl implements AuthRestService {

	private final AuthService authService;

	@Autowired
	public AuthRestServiceImpl(AuthService authService) {
		this.authService = authService;
	}

	@Override
	public AuthEntityResponseVO login(AuthLogInUserVO logInUser) throws HttpStatusException {
		return authService.login(logInUser);
	}

	@Override
	public AuthEntityResponseVO signup(AuthSignUpUserVO signUpUser) throws HttpAuthenticationException {
		return authService.signup(signUpUser);
	}

	@Override
	public AuthEntityResponseVO loginFacebook(SatellizerPayloadVO payload) throws HttpStatusException {
		return authService.linkFacebook(payload);
	}

	@Override
	public AuthEntityResponseVO loginGoogle(SatellizerPayloadVO payload) throws HttpStatusException {
		return authService.linkGoogle(payload);
	}

	@Override
	public UserVO unlink(String provider) throws HttpStatusException {
		return authService.unlink(provider);
	}

	@Override
	public void logout() throws HttpAuthenticationException {
		authService.logout();
	}

	@Override
	public void verify(String token) throws HttpStatusException {
		// TODO: make token go throught header
		authService.verify(token);
	}

	@Override
	public void forgotPassword(ForgotPasswordVO forgotPasswordVO) throws HttpContentNotFoundException {
		authService.forgotPassword(forgotPasswordVO.getEmailOrUsername());
	}

	@Override
	public void passwordForgotten(String token, PasswordForgottenVO forgotPasswordVO) throws HttpStatusException {
		authService.passwordForgotten(token, forgotPasswordVO.getNewPassword());
	}
}
