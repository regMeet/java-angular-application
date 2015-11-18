package com.company.project.webservice.filter;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.company.project.services.utils.AuthUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

public class AuthFilter implements Filter {
	private final static Logger log = Logger.getLogger(AuthFilter.class);

	private static final String AUTH_ERROR_MSG = "Please make sure your request has an Authorization header";
	private static final String EXPIRE_ERROR_MSG = "Token has expired";
	private static final String JWT_ERROR_MSG = "Unable to parse JWT";
	private static final String JWT_INVALID_MSG = "Invalid JWT token";

	boolean checkAuthorization = true;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// TODO: use JJWT
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String authHeader = httpRequest.getHeader(AuthUtils.AUTH_HEADER_KEY);

		if (checkAuthorization) {
			if (StringUtils.isBlank(authHeader) || authHeader.split(" ").length != 2) {
				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, AUTH_ERROR_MSG);
			} else {
				JWTClaimsSet claimSet = null;
				try {
					claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
				} catch (ParseException e) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, JWT_ERROR_MSG);
					return;
				} catch (JOSEException e) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, JWT_INVALID_MSG);
					return;
				}

				// ensure that the token is not expired
				if (new DateTime(claimSet.getExpirationTime()).isBefore(DateTime.now())) {
					httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, EXPIRE_ERROR_MSG);
				} else {
					chain.doFilter(request, response);
				}
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String authorizationParam = filterConfig.getInitParameter("checkAuthorization");
		checkAuthorization = Boolean.parseBoolean(authorizationParam);
		log.debug("authorizationParam --- " + authorizationParam);
	}

	@Override
	public void destroy() {
	}

}
