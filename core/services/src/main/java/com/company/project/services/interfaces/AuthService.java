package com.company.project.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import com.company.project.VO.AuthEntityResponseVO;
import com.company.project.VO.AuthLogInUserVO;
import com.company.project.VO.AuthSignUpUserVO;
import com.company.project.VO.SatellizerPayloadVO;
import com.company.project.VO.UserVO;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpContentNotFoundException;
import com.company.project.api.exception.HttpStatusException;
import com.company.project.persistence.entities.User;
import com.company.project.security.UserContext;

public interface AuthService {

	public AuthEntityResponseVO login(AuthLogInUserVO logInUser) throws HttpStatusException;

	public AuthEntityResponseVO signup(AuthSignUpUserVO signUpUser) throws HttpAuthenticationException;

	public AuthEntityResponseVO linkFacebook(SatellizerPayloadVO satellizerPayload) throws HttpStatusException;

	public AuthEntityResponseVO linkGoogle(SatellizerPayloadVO p) throws HttpStatusException;

	public UserVO unlink(String provider) throws HttpStatusException;

	public UserContext authenticate(String username, String password) throws HttpStatusException;

	public void checkLoadCredentials(String token) throws HttpAuthenticationException;

	public UserDetails getUserDetails(String accessToken) throws HttpAuthenticationException;

	public void logout();

	public UserDetails currentUserDetails();

	public User currentUser();

	public boolean hasAuthority(String authority);

	public void verify(String token) throws HttpAuthenticationException;

	public void forgotPassword(String emailOrUsername) throws HttpContentNotFoundException;

}
