package com.company.project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpError;
import com.company.project.persistence.entities.User;
import com.company.project.security.interfaces.TokenManager;

/**
 * Implements simple token manager, that keeps a single token for each user. If user logs in again, older token is invalidated.
 */
@Service("tokenManager")
public class TokenManagerImpl implements TokenManager {
	private static final Logger log = Logger.getLogger(TokenManagerImpl.class);
	private static final String TOKEN_SECRET = "aliceinwonderland";

	@Override
	public String createNewToken(User user) {
		LocalDateTime today = LocalDateTime.now();
		JwtBuilder jwtBuilder = Jwts.builder();
		jwtBuilder.setSubject(Long.toString(user.getIdUser()));
		jwtBuilder.setIssuedAt(today.toDate());
		jwtBuilder.setExpiration(today.plusDays(14).toDate());
		jwtBuilder.signWith(SignatureAlgorithm.HS512, TOKEN_SECRET);
		return jwtBuilder.compact();
	}

	@Override
	public String decodeToken(String accessToken) throws HttpAuthenticationException {
		// TODO: chequear que sea el ultimo token -- last log out
		// ensure that the token is not expired

		String userId = null;
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(accessToken);
			userId = claimsJws.getBody().getSubject();
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			log.info("There was an error decoding the access token " + e.getMessage());
			throw new HttpAuthenticationException(HttpError.UNAUTHORIZED_API);
		}

		return userId;
	}

}
