package com.company.project.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import com.company.project.security.interfaces.AuthenticationService;

/**
 * Takes care of HTTP request/response pre-processing for login/logout and token check. Login can be performed on any URL, logout only on specified
 * {@link #logoutLink}. All the interaction with Spring Security should be performed via {@link AuthenticationService}.
 * <p>
 * {@link SecurityContextHolder} is used here only for debug outputs. While this class is configured to be used by Spring Security (configured filter
 * on FORM_LOGIN_FILTER position), but it doesn't really depend on it at all.
 */
@Service("tokenAuthenticationFilter")
public final class TokenAuthenticationFilter extends GenericFilterBean {

	private static final String HEADER_TOKEN = "Authorization";

	private final AuthenticationService authenticationService;

	@Autowired
	public TokenAuthenticationFilter(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println(" *** MyAuthenticationFilter.doFilter");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		boolean authenticated = checkToken(httpRequest, httpResponse);

//		if (authenticated){
//			chain.doFilter(request, response);
//		}else {
//			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//		}
		chain.doFilter(request, response);
		System.out.println(" === AUTHENTICATION: " + SecurityContextHolder.getContext().getAuthentication());
		
//		UserDetails currentUser = authenticationService.currentUser();
//		System.out.println(currentUser);
	}

	/** Returns true, if request contains valid authentication token. */
	private boolean checkToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		String token = httpRequest.getHeader(HEADER_TOKEN);
		if (token == null) {
			return false;
		}

		if (authenticationService.checkToken(token)) {
			System.out.println(" *** " + HEADER_TOKEN + " valid for: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return true;
		} else {
			System.out.println(" *** Invalid " + HEADER_TOKEN + ' ' + token);
		}
		return false;
	}
}
