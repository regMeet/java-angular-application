package com.company.project.webservice.interfaces;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

@PermitAll
@RequestMapping("/auth")
public interface AuthRestService {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody AuthEntityResponseVO login(@RequestBody @Valid AuthLogInUserVO logInUser) throws HttpStatusException;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public @ResponseBody AuthEntityResponseVO signup(@RequestBody @Valid AuthSignUpUserVO signupUser) throws HttpAuthenticationException;

	@RequestMapping(value = "/facebook", method = RequestMethod.POST)
	public @ResponseBody AuthEntityResponseVO loginFacebook(@RequestBody @Valid SatellizerPayloadVO payload) throws HttpStatusException;

	@RequestMapping(value = "/google", method = RequestMethod.POST)
	public @ResponseBody AuthEntityResponseVO loginGoogle(@RequestBody @Valid SatellizerPayloadVO payload) throws HttpStatusException;

	@PreAuthorize("isFullyAuthenticated()")
	@RequestMapping(value = "/unlink/{provider}", method = RequestMethod.GET)
	public @ResponseBody UserVO unlink(@PathVariable("provider") String provider) throws HttpStatusException;

	@PreAuthorize("isFullyAuthenticated()")
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody void logout() throws HttpAuthenticationException;

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public @ResponseBody void verify(@RequestParam(value = "token") String token) throws HttpStatusException;

	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public @ResponseBody void forgotPassword(@RequestBody @Valid ForgotPasswordVO forgotPasswordVO) throws HttpContentNotFoundException;

	@RequestMapping(value = "/password-forgotten", method = RequestMethod.POST)
	public @ResponseBody void passwordForgotten(@RequestParam(value = "token") String token, @RequestBody @Valid PasswordForgottenVO forgotPasswordVO)
			throws HttpStatusException;

}
