package com.company.project.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.company.project.VO.ErrorVO;
import com.company.project.api.exception.HttpError;
import com.google.gson.Gson;

/**
 * {@link AuthenticationEntryPoint} that rejects all requests. Login-like function is featured in {@link TokenAuthenticationFilter} and this does not
 * perform or suggests any redirection. This object is hit whenever user is not authorized (anonymous) and secured resource is requested.
 */
@Component
public final class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
	private static final String APPLICATION_JSON = "application/json";

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		response.setContentType(APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ErrorVO error = new ErrorVO();
		error.setMessage(HttpError.UNAUTHORIZED.getMessageKey());

		response.getWriter().println(new Gson().toJson(error));
	}
}