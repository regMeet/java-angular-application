package com.company.project.webservice.implementations;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.VO.AuthEntityResponseVO;
import com.company.project.VO.AuthLogInUserVO;
import com.company.project.VO.AuthSignUpUserVO;
import com.company.project.VO.SatellizerPayloadVO;
import com.company.project.VO.UserVO;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpStatusException;
import com.company.project.services.interfaces.AuthService;
import com.company.project.services.utils.AuthUtils;
import com.company.project.webservice.interfaces.AuthRestService;
import com.google.common.base.Optional;

@RestController
public class AuthRestServiceImpl implements AuthRestService {

	private final AuthService authService;

	@Autowired
	public AuthRestServiceImpl(AuthService authService) {
		this.authService = authService;
	}

	@Override
	public AuthEntityResponseVO login(AuthLogInUserVO logInUser, HttpServletRequest request) throws HttpAuthenticationException {

		return authService.login(logInUser, request.getRemoteHost());
	}

	@Override
	public AuthEntityResponseVO signup(AuthSignUpUserVO signUpUser, HttpServletRequest request) throws HttpAuthenticationException {

		return authService.signup(signUpUser, request.getRemoteHost());
	}

	@Override
	public AuthEntityResponseVO loginFacebook(SatellizerPayloadVO payload, HttpServletRequest request) throws HttpStatusException {

		Long subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		return authService.linkFacebook(payload, Optional.fromNullable(subject), request.getRemoteHost());
	}

	@Override
	public AuthEntityResponseVO loginGoogle(SatellizerPayloadVO payload, String authorizationHeader, HttpServletRequest request)
			throws HttpStatusException {

		// TODO: clean up this code
		// @RequestParam("remoteHost") String remoteHost
		String remoteHost = request.getRemoteHost();

		Long subject = AuthUtils.getSubject(authorizationHeader);
		return authService.linkGoogle(payload, Optional.fromNullable(subject), remoteHost);
	}

	@Override
	public UserVO unlink(String provider, String authorizationHeader) throws HttpStatusException {

		Long userId = AuthUtils.getSubject(authorizationHeader);
		return authService.unlink(provider, userId);
	}
}
