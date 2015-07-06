/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.company.project.persistence.dao.implementations;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.persistence.dao.implementations.base.BaseDAOImpl;
import com.company.project.persistence.dao.interfaces.UserDAO;
import com.company.project.persistence.entities.User;
import com.company.project.persistence.entities.User.Provider;
import com.google.common.base.Optional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {
	final static Logger log = Logger.getLogger(UserDAOImpl.class);

	private static final long serialVersionUID = 1L;

	public Optional<User> findByUsername(String username) {
		User foundUser = (User) em.createNamedQuery(User.FIND_BY_USERNAME).setParameter("email", username).getSingleResult();
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

}
