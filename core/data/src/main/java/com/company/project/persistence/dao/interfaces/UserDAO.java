package com.company.project.persistence.dao.interfaces;

import com.company.project.persistence.dao.interfaces.base.BaseDAO;
import com.company.project.persistence.entities.User;
import com.company.project.persistence.entities.User.Provider;
import com.google.common.base.Optional;

public interface UserDAO extends BaseDAO<User> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Optional<User> findByProvider(Provider provider, String providerId);

}