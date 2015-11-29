package com.company.project.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.services.interfaces.AuthService;

/**
 * Takes care of HTTP request/response pre-processing for login/logout and token check. Login can be performed on any URL, logout only on specified
 * {@link #logoutLink}. All the interaction with Spring Security should be performed via {@link SecurityService}.
 * <p>
 * {@link SecurityContextHolder} is used here only for debug outputs. While this class is configured to be used by Spring Security (configured filter
 * on FORM_LOGIN_FILTER position), but it doesn't really depend on it at all.
 */
@Service("tokenAuthenticationFilter")
public final class TokenAuthenticationFilter extends GenericFilterBean {
	private static final Logger log = Logger.getLogger(TokenAuthenticationFilter.class);

	private static final String HEADER_TOKEN = "Authorization";

	private final AuthService authService;

	@Autowired
	public TokenAuthenticationFilter(AuthService authService) {
		this.authService = authService;
		System.out.println("asas");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String authToken = httpRequest.getHeader(HEADER_TOKEN);

		if (StringUtils.isNotBlank(authToken)) {
			try {
				authService.checkLoadCredentials(authToken);
			} catch (HttpAuthenticationException e) {
				log.info("There was an error with the token specified in the request");
			}
		}

		chain.doFilter(request, response);
	}
}
