package com.company.project.security;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.company.project.persistence.entities.User;
import com.company.project.security.interfaces.TokenManager;
import com.company.project.security.util.TokenInfo;
import com.company.project.security.util.UserContext;
import com.company.project.services.interfaces.UserService;
import com.company.project.services.utils.AuthUtils;
import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

/**
 * Implements simple token manager, that keeps a single token for each user. If user logs in again, older token is invalidated.
 */
@Service("tokenManager")
public class TokenManagerImpl implements TokenManager {
	final static Logger log = Logger.getLogger(TokenManagerImpl.class);

	private final UserService userService;

	@Autowired
	public TokenManagerImpl(UserService userService) {
		this.userService = userService;
	}

	private Map<String, UserDetails> validUsers = new HashMap<>();

	/**
	 * This maps system users to tokens because equals/hashCode is delegated to User entity. This can store either one token or list of them for each
	 * user, depending on what you want to do. Here we store single token, which means, that any older tokens are invalidated.
	 */
	private Map<UserDetails, TokenInfo> tokens = new HashMap<>();

	@Override
	public TokenInfo createNewToken(UserDetails userDetails) {
		String token;
		do {
			token = generateToken();
		} while (validUsers.containsKey(token));

		TokenInfo tokenInfo = new TokenInfo(token, userDetails);
		removeUserDetails(userDetails);
		UserDetails previous = validUsers.put(token, userDetails);
		if (previous != null) {
			System.out.println(" *** SERIOUS PROBLEM HERE - we generated the same token (randomly?)!");
			return null;
		}
		tokens.put(userDetails, tokenInfo);

		return tokenInfo;
	}

	private String generateToken() {
		byte[] tokenBytes = new byte[32];
		new SecureRandom().nextBytes(tokenBytes);
		return new String(Base64.encode(tokenBytes), StandardCharsets.UTF_8);
	}

	@Override
	public void removeUserDetails(UserDetails userDetails) {
		TokenInfo token = tokens.remove(userDetails);
		if (token != null) {
			validUsers.remove(token.getToken());
		}
	}

	@Override
	public UserDetails removeToken(String token) {
		UserDetails userDetails = validUsers.remove(token);
		if (userDetails != null) {
			tokens.remove(userDetails);
		}
		return userDetails;
	}

	@Override
	public UserDetails getUserDetails(String token) {
		UserDetails userDetail = null;

		try {
			JWTClaimsSet claimSet = (JWTClaimsSet) AuthUtils.decodeToken(token);

			String userId = claimSet.getSubject();

			// ensure that the token is not expired
			if (new DateTime(claimSet.getExpirationTime()).isBefore(DateTime.now())) {
				log.warn("Token expired for userId: " + userId);
			} else {
				Optional<User> foundUser = userService.findById(Long.valueOf(userId));
				if (!foundUser.isPresent()) {
					log.error("Errog getting inexistent user: " + userId);
				} else {
					// TODO: chequear que sea el ultimo token

					userDetail = new UserContext(foundUser.get());
				}
			}

		} catch (ParseException e) {
			log.error("Invalid token");
		} catch (JOSEException e) {
			log.error("Invalid token");
		}

		return userDetail;
	}

	@Override
	public Collection<TokenInfo> getUserTokens(UserDetails userDetails) {
		return Arrays.asList(tokens.get(userDetails));
	}

	@Override
	public Map<String, UserDetails> getValidUsers() {
		return Collections.unmodifiableMap(validUsers);
	}
}
