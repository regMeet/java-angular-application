package com.company.project.security;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.company.project.security.interfaces.AuthenticationService;
import com.company.project.security.interfaces.TokenManager;
import com.company.project.security.util.TokenInfo;

/**
 * Service responsible for all around authentication, token checks, etc. This class does not care about HTTP protocol at all.
 */
@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private ApplicationContext applicationContext;

	// AuthenticationManager comes from Spring
	private final AuthenticationManager authenticationManager;
	private final TokenManager tokenManager;

	@Autowired
	public AuthenticationServiceImpl(AuthenticationManager authenticationManager, TokenManager tokenManager) {
		this.authenticationManager = authenticationManager;
		this.tokenManager = tokenManager;
	}

	@PostConstruct
	public void init() {
	}

	@Override
	public TokenInfo authenticate(String login, String password) {
		// Here principal=username, credentials=password
		Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
		try {
			authentication = authenticationManager.authenticate(authentication);
			// Here principal=UserDetails (UserContext in our case), credentials=null (security reasons)
			SecurityContextHolder.getContext().setAuthentication(authentication);

			if (authentication.getPrincipal() != null) {
				UserDetails userContext = (UserDetails) authentication.getPrincipal();
				TokenInfo newToken = tokenManager.createNewToken(userContext);
				if (newToken == null) {
					return null;
				}
				return newToken;
			}
		} catch (AuthenticationException e) {
		}
		return null;
	}

	@Override
	public boolean checkToken(String token) {

		UserDetails userDetails = tokenManager.getUserDetails(token);
		if (userDetails == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		UsernamePasswordAuthenticationToken securityToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(securityToken);

		return true;
	}

	@Override
	public void logout(String token) {
		UserDetails logoutUser = tokenManager.removeToken(token);
		SecurityContextHolder.clearContext();
	}

	@Override
	public UserDetails currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return (UserDetails) authentication.getPrincipal();
	}
	
	@Override
	public boolean hasAuthority(String authority) {
		return currentUser() != null?
				currentUser().getAuthorities().contains(new SimpleGrantedAuthority(authority)):false;
	}
}
