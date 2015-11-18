package com.company.project.security.interfaces;

import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.persistence.entities.User;

/**
 * Manages tokens - separated from {@link SecurityService}, so we can implement and plug various policies.
 */
public interface TokenManager {

	String createNewToken(User user);

	String decodeToken(String token) throws HttpAuthenticationException;

}
