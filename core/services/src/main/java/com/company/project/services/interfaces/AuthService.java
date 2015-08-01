package com.company.project.services.interfaces;

import com.company.project.VO.AuthEntityResponseVO;
import com.company.project.VO.AuthLogInUserVO;
import com.company.project.VO.AuthSignUpUserVO;
import com.company.project.VO.SatellizerPayloadVO;
import com.company.project.VO.UserVO;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpStatusException;
import com.google.common.base.Optional;

public interface AuthService {

	public AuthEntityResponseVO login(AuthLogInUserVO logInUser, String remoteHost) throws HttpAuthenticationException;

	public AuthEntityResponseVO signup(AuthSignUpUserVO signUpUser, String remoteHost) throws HttpAuthenticationException;

	public AuthEntityResponseVO linkFacebook(SatellizerPayloadVO satellizerPayload, Optional<Long> userId, String remoteHost)
			throws HttpStatusException;

	public AuthEntityResponseVO linkGoogle(SatellizerPayloadVO p, Optional<Long> userId, String remoteHost) throws HttpStatusException;

	public UserVO unlink(String provider, Long userId) throws HttpStatusException;

}
