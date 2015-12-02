package com.company.project.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.company.project.persistence.dao.interfaces.UserDAO;
import com.company.project.persistence.entities.User;
import com.company.project.persistence.entities.User.AccountStatus;
import com.company.project.services.implementations.AuthServiceImpl;
import com.google.common.base.Optional;

/**
 * Implements Spring Security {@link UserDetailsService} that is injected into authentication provider in configuration XML. It interacts with domain,
 * loads user details and wraps it into {@link UserContext} which implements Spring Security {@link UserDetails}.
 */
@Service("securityUserService")
public class SecurityUserService implements UserDetailsService {
	private static final Logger log = Logger.getLogger(SecurityUserService.class);

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
			User user = foundUser.get();
			AccountStatus status = user.getStatus();
			if (!status.equals(User.AccountStatus.TO_BE_VERIFIED) && !status.equals(User.AccountStatus.SUSPENDED)) {
				return new UserContext(user);
			} else {
				log.error("Error trying to log in the user " + user);
			}
		}

		throw new UsernameNotFoundException("User " + username + " not found");
	}

}
