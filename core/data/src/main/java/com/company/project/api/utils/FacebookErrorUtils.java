package com.company.project.api.utils;

import org.apache.log4j.Logger;

import com.company.project.api.entities.facebook.base.FacebookEntity;
import com.company.project.api.entities.facebook.base.FacebookError;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpError;
import com.company.project.api.exception.HttpStatusException;

public class FacebookErrorUtils {
	private static final Logger log = Logger.getLogger(FacebookErrorUtils.class);
	private static final String OAUTH_EXCEPTION = "OAuthException";
	private static final String EXPIRED_SESSION = "463";
	private static final String USER_LOGGED_OUT = "467";
	private static final String INVALID_TOKEN = "2500";
	private static final String OAUTH_EXCEPTION_CODE = "190";
	private static final String MALFORMED_ACCESS_TOKEN = "Malformed access token";

	public static void checkErrors(FacebookEntity entity) throws HttpAuthenticationException, HttpStatusException {
		FacebookError error = entity.getError();
		if (error != null) {
			if (error.getType().equals(OAUTH_EXCEPTION)) {
				String errorSubcode = error.getSubcode();
				if (errorSubcode != null) {
					if (errorSubcode.equals(EXPIRED_SESSION)) {
						log.info(error);
						// throw new HttpAuthenticationException(HttpError.EXPIRED_SESSION_FACEBOOK);
					} else if (errorSubcode.equals(USER_LOGGED_OUT)) {
						log.info(error);
					} else {
						// add validation for a new error
						log.error(error);
					}
				} else {
					String code = error.getCode();
					if (code.equals(INVALID_TOKEN)) {
						log.info(error);
						// throw new HttpAuthenticationException(HttpError.INVALID_TOKEN_FACEBOOK);
					} else if (code.equals(OAUTH_EXCEPTION_CODE) && error.getMessage().contains(MALFORMED_ACCESS_TOKEN)) {
						log.info(error);
						// throw new HttpAuthenticationException(HttpError.INVALID_TOKEN_FACEBOOK);
					} else {
						// check if the user need a new special permission for the application or it coult happened that
						// the user has de-authorized the application
						log.error(error);
					}
				}
				throw new HttpAuthenticationException(HttpError.UNAUTHORIZED_FACEBOOK);
			} else {
				// validate new kind of errors
				log.error(error);
				throw new HttpStatusException(HttpError.GENERIC_FACEBOOK_ERROR);
			}
		}

	}
}