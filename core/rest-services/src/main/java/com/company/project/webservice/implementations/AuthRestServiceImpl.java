package com.company.project.webservice.implementations;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping("/auth")
public class AuthRestServiceImpl implements AuthRestService {

	private final AuthService authService;

	@Autowired
	public AuthRestServiceImpl(AuthService authService) {
		this.authService = authService;
	}

	@Override
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody AuthEntityResponseVO login(@RequestBody @Valid AuthLogInUserVO logInUser, HttpServletRequest request)
			throws HttpAuthenticationException {

		return authService.login(logInUser, request.getRemoteHost());
	}

	@Override
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public @ResponseBody AuthEntityResponseVO signup(@RequestBody @Valid AuthSignUpUserVO signUpUser, HttpServletRequest request)
			throws HttpAuthenticationException {

		return authService.signup(signUpUser, request.getRemoteHost());
	}

	@Override
	@RequestMapping(value = "/facebook", method = RequestMethod.POST)
	public @ResponseBody AuthEntityResponseVO loginFacebook(@RequestBody @Valid SatellizerPayloadVO payload, HttpServletRequest request)
			throws HttpStatusException {

		Long subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		return authService.linkFacebook(payload, Optional.fromNullable(subject), request.getRemoteHost());
	}

	@Override
	@RequestMapping(value = "/google", method = RequestMethod.POST)
	public @ResponseBody AuthEntityResponseVO loginGoogle(@RequestBody @Valid SatellizerPayloadVO payload,
			@RequestHeader(value = AuthUtils.AUTH_HEADER_KEY, required = false) String authorizationHeader, HttpServletRequest request)
			throws HttpStatusException {

		// @RequestParam("remoteHost") String remoteHost
		String remoteHost = request.getRemoteHost();

		Long subject = AuthUtils.getSubject(authorizationHeader);
		return authService.linkGoogle(payload, Optional.fromNullable(subject), remoteHost);
	}

	@Override
	@RequestMapping(value = "/unlink/{provider}", method = RequestMethod.GET)
	public @ResponseBody UserVO unlink(@PathVariable("provider") String provider,
			@RequestHeader(value = AuthUtils.AUTH_HEADER_KEY, required = true) String authorizationHeader) throws HttpStatusException {

		Long userId = AuthUtils.getSubject(authorizationHeader);
		return authService.unlink(provider, userId);
	}
}
