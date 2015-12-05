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
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpError;
import com.company.project.persistence.entities.User;
import com.company.project.security.interfaces.TokenManager;
import com.company.project.services.utils.LocalDateUtils;

/**
 * Implements simple token manager, that keeps a single token for each user. If user logs in again, older token is invalidated.
 */
@Service("tokenManager")
public class TokenManagerImpl implements TokenManager {
	private static final Logger log = Logger.getLogger(TokenManagerImpl.class);
	private static final String TOKEN_SECRET = "aliceinwonderland";

	private static final int DAY_EXPIRATION = 24; // 24 hours = 1 day
	private static final int LOGIN_EXPIRATION = 14; // hours
	private static final int PASSWORD_EXPIRATION = 1; // hours

	@Override
	public String createNewToken(User user, TokenType tokenType, int duration) {
		DateTime today = LocalDateUtils.getTodayDateTime();
		JwtBuilder jwtBuilder = Jwts.builder();
		jwtBuilder.setSubject(Long.toString(user.getIdUser()));
		jwtBuilder.setAudience(tokenType.getPayload());
		jwtBuilder.setIssuedAt(today.toDate());
		if (duration > 0) {
			jwtBuilder.setExpiration(today.plusHours(duration).toDate());
		}
		jwtBuilder.signWith(SignatureAlgorithm.HS512, TOKEN_SECRET);
		return jwtBuilder.compact();
	}

	@Override
	public String createLoginToken(User user) {
		return createNewToken(user, TokenType.LOGIN, DAY_EXPIRATION * LOGIN_EXPIRATION);
	}

	@Override
	public String createVerifyToken(User user) {
		return createNewToken(user, TokenType.VERIFY, DAY_EXPIRATION * PASSWORD_EXPIRATION);
	}

	@Override
	public String createForgotPasswordToken(User user) {
		return createNewToken(user, TokenType.FORGOT_PASS, -1);
	}

	@Override
	public Claims decodeToken(String accessToken) throws HttpAuthenticationException {
		Claims claimsBody = null;
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(accessToken);
			claimsBody = claimsJws.getBody();
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			log.info("There was an error decoding the access token " + e.getMessage());
			throw new HttpAuthenticationException(HttpError.UNAUTHORIZED_API);
		}

		return claimsBody;
	}

	public static enum TokenType {
		LOGIN("lo"), FORGOT_PASS("fo"), VERIFY("ve");

		private String payload;

		TokenType(String payload) {
			this.payload = payload;
		}

		public String getPayload() {
			return payload;
		}

		public static TokenType fromPayload(String payload) {
			for (TokenType tokenType : values()) {
				if (tokenType.getPayload().compareTo(payload) == 0) {
					return tokenType;
				}
			}
			throw new IllegalArgumentException("There is no TokenType with value: " + payload);
		}
	}

}
