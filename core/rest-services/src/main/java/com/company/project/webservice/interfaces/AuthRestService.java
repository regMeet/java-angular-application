package com.company.project.webservice.interfaces;

import javax.servlet.http.HttpServletRequest;

import com.company.project.VO.AuthEntityResponseVO;
import com.company.project.VO.AuthLogInUserVO;
import com.company.project.VO.AuthSignUpUserVO;
import com.company.project.VO.SatellizerPayloadVO;
import com.company.project.VO.UserVO;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpStatusException;

public interface AuthRestService {

	public AuthEntityResponseVO login(AuthLogInUserVO logInUser, HttpServletRequest request) throws HttpAuthenticationException;

	public abstract AuthEntityResponseVO signup(AuthSignUpUserVO signupUser, HttpServletRequest request) throws HttpAuthenticationException;

	public abstract AuthEntityResponseVO loginFacebook(SatellizerPayloadVO payload, HttpServletRequest request) throws HttpStatusException;

	public abstract AuthEntityResponseVO loginGoogle(SatellizerPayloadVO payload, String authorizationHeader, HttpServletRequest request)
			throws HttpStatusException;

	public abstract UserVO unlink(String provider, String authorizationHeader) throws HttpStatusException;

}
