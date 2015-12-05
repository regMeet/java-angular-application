package com.company.project.security.interfaces;

import io.jsonwebtoken.Claims;

import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.persistence.entities.User;
import com.company.project.security.TokenManagerImpl.TokenType;

/**
 * Manages tokens - separated from {@link SecurityService}, so we can implement and plug various policies.
 */
public interface TokenManager {

	String createNewToken(User user, TokenType tokenType, int duration);

	String createLoginToken(User user);

	String createVerifyToken(User user);

	String createForgotPasswordToken(User user);

	Claims decodeToken(String token) throws HttpAuthenticationException;

}
