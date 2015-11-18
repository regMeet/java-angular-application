package com.company.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.company.project.persistence.dao.interfaces.UserDAO;
import com.company.project.persistence.entities.User;
import com.google.common.base.Optional;

/**
 * Implements Spring Security {@link UserDetailsService} that is injected into authentication provider in configuration XML. It interacts with domain,
 * loads user details and wraps it into {@link UserContext} which implements Spring Security {@link UserDetails}.
 */
@Service("securityUserService")
public class SecurityUserService implements UserDetailsService {

	private UserDAO userDAO;

	@Autowired
	public SecurityUserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * This will be called from
	 * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider#retrieveUser(java.lang.String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)}
	 * when {@link SecurityService#authenticate(java.lang.String, java.lang.String)} calls
	 * {@link AuthenticationManager#authenticate(org.springframework.security.core.Authentication)}. Easy.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> foundUser = userDAO.findByEmail(username);
		if (!foundUser.isPresent()) {
			foundUser = userDAO.findByUsername(username);
		}

		if (foundUser.isPresent()) {
			return new UserContext(foundUser.get());
		} else {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
	}

}
