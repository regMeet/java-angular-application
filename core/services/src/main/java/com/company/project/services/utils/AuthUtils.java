package com.company.project.services.utils;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.company.project.VO.AuthEntityResponseVO;
import com.company.project.VO.AuthUserVO;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpError;
import com.company.project.persistence.entities.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public final class AuthUtils {
	final static Logger log = Logger.getLogger(AuthUtils.class);

	private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
	private static final String TOKEN_SECRET = "aliceinwonderland";
	public static final String AUTH_HEADER_KEY = "Authorization";

	public static Long getSubject(String authorizationAccessToken) throws HttpAuthenticationException {
		Long userId = null;

		if (StringUtils.isNotBlank(authorizationAccessToken)) {
			try {
				String subject = decodeToken(authorizationAccessToken).getSubject();
				userId = Long.parseLong(subject);
			} catch (ParseException e) {
				log.error("There has been an error decoding the access token " + e.getMessage());
				throw new HttpAuthenticationException(HttpError.UNAUTHORIZED_API);
			} catch (JOSEException e) {
				log.error("There has been an error with the access token " + e.getMessage());
				throw new HttpAuthenticationException(HttpError.UNAUTHORIZED_API);
			}
		}
		return userId;
	}

	public static ReadOnlyJWTClaimsSet decodeToken(String authHeader) throws ParseException, JOSEException {
		SignedJWT signedJWT = SignedJWT.parse(getSerializedToken(authHeader));
		if (signedJWT.verify(new MACVerifier(TOKEN_SECRET))) {
			return signedJWT.getJWTClaimsSet();
		} else {
			throw new JOSEException("Signature verification failed");
		}
	}

	public static AuthEntityResponseVO createToken(String host, User user) throws JOSEException {
		JWTClaimsSet claim = new JWTClaimsSet();
		long sub = user.getIdUser();
		claim.setSubject(Long.toString(sub));
		claim.setIssuer(host);
		claim.setIssueTime(DateTime.now().toDate());
		claim.setExpirationTime(DateTime.now().plusDays(14).toDate());

		JWSSigner signer = new MACSigner(TOKEN_SECRET);
		SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
		jwt.sign(signer);

		return new AuthEntityResponseVO(jwt.serialize(), new AuthUserVO(user));
	}

	public static String getSerializedToken(String authHeader) {
		return authHeader.split(" ")[1];
	}
}
