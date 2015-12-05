/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.company.project.persistence.dao.implementations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.persistence.dao.implementations.base.BaseDAOImpl;
import com.company.project.persistence.dao.interfaces.UserDAO;
import com.company.project.persistence.entities.User;
import com.company.project.persistence.entities.User.AccountStatus;
import com.company.project.persistence.entities.User.Provider;
import com.company.project.utils.LocalDateUtils;
import com.google.common.base.Optional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {
	private static final long serialVersionUID = 5519893069484786026L;
	private final static Logger log = Logger.getLogger(UserDAOImpl.class);
	private static final int FIRST_ATTEMPT = 1;

	@Value("${application.max.attempts}")
	private int maxAttempts;

	private Map<Long, UserAttempt> loginAttempts = new HashMap<>();

	public Optional<User> findByUsername(String username) {
		User foundUser = null;
		try {
			foundUser = (User) em.createNamedQuery(User.FIND_BY_USERNAME).setParameter("username", username).getSingleResult();
		} catch (NoResultException nre) {
			log.info(String.format("The user with username: %s has not been found", username));
		}

		return Optional.fromNullable(foundUser);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		User foundUser = null;
		try {
			foundUser = (User) em.createNamedQuery(User.FIND_BY_EMAIL).setParameter("email", email).getSingleResult();
		} catch (NoResultException nre) {
			log.info(String.format("The user with email: %s has not been found", email));
		}
		return Optional.fromNullable(foundUser);
	}

	@Override
	public Optional<User> findByProvider(Provider provider, String providerId) {
		User foundUser = null;
		try {
			String namedQuery = String.format(User.FIND_BY, provider.capitalize());
			foundUser = (User) em.createNamedQuery(namedQuery).setParameter(provider.getName(), providerId).getSingleResult();
		} catch (NoResultException nre) {
			log.info(String.format("The %s user: %s has not been found", provider, providerId));
		}
		return Optional.fromNullable(foundUser);
	}

	@Override
	public Optional<User> findByEmailOrUsername(String emailOrUsername) {
		Optional<User> foundUser = findByEmail(emailOrUsername);
		if (!foundUser.isPresent()) {
			foundUser = findByUsername(emailOrUsername);
		}

		return foundUser;
	}

	@Data
	@AllArgsConstructor
	public static class UserAttempt {
		int times;
		Date lastAttempt;
	}

	@Override
	public UserAttempt getUserAttempts(User user) {
		return loginAttempts.get(user.getIdUser());
	}

	@Override
	public void updateFailAttempts(User user) {
		Long userId = user.getIdUser();
		UserAttempt userAttempt = loginAttempts.get(userId);

		if (userAttempt == null) {
			userAttempt = new UserAttempt(FIRST_ATTEMPT, LocalDateUtils.getTodayDate());
			loginAttempts.put(userId, userAttempt);
		} else {
			Date lastAttempt = userAttempt.getLastAttempt();
			boolean isWithin3Hours = LocalDateUtils.isWithin3Hours(lastAttempt);
			if (isWithin3Hours) {
				int times = userAttempt.getTimes() + 1;
				if (times >= maxAttempts) {
					user.setStatus(AccountStatus.SUSPENDED);
				}
				userAttempt.setTimes(times);
				userAttempt.setLastAttempt(LocalDateUtils.getTodayDate());

			} else {
				// Reset number of attempts
				userAttempt.setTimes(FIRST_ATTEMPT);
				userAttempt.setLastAttempt(LocalDateUtils.getTodayDate());
			}
		}
	}

	@Override
	public void resetFailAttempts(User user) {
		loginAttempts.remove(user.getIdUser());
	}

}
