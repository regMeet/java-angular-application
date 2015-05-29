/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.company.project.persistence.dao.implementations;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.persistence.dao.implementations.base.BaseDAOImpl;
import com.company.project.persistence.dao.interfaces.UserDAO;
import com.company.project.persistence.entities.Users;
import com.google.common.base.Optional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class UserDAOImpl extends BaseDAOImpl<Users> implements UserDAO {

	private static final long serialVersionUID = 1L;

	public Optional<Users> findByUsername(String username){
		Users foundUser = (Users) em.createNamedQuery(Users.FIND_BY_USERNAME).setParameter("email", username).getSingleResult();
		return Optional.fromNullable(foundUser);
	}

	public Optional<Users> findByEmail(String email){
		Users foundUser = (Users) em.createNamedQuery(Users.FIND_BY_EMAIL).setParameter("email", email).getSingleResult();
		return Optional.fromNullable(foundUser);
	}

}
