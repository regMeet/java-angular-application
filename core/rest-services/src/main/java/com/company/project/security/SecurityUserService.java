package com.company.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.company.project.persistence.entities.User;
import com.company.project.security.interfaces.AuthenticationService;
import com.company.project.security.util.UserContext;
import com.company.project.services.interfaces.UserService;
import com.google.common.base.Optional;

/**
 * Implements Spring Security {@link UserDetailsService} that is injected into authentication provider in configuration XML. It interacts with domain,
 * loads user details and wraps it into {@link UserContext} which implements Spring Security {@link UserDetails}.
 */
@Service("securityUserService")
public class SecurityUserService implements UserDetailsService {

	private UserService userService;

	@Autowired
	public SecurityUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * This will be called from
	 * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider#retrieveUser(java.lang.String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)}
	 * when {@link AuthenticationService#authenticate(java.lang.String, java.lang.String)} calls
	 * {@link AuthenticationManager#authenticate(org.springframework.security.core.Authentication)}. Easy.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> foundUsername = userService.findByUsername(username);

		if (foundUsername.isPresent()) {
			return new UserContext(foundUsername.get());
		} else {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
	}

}
