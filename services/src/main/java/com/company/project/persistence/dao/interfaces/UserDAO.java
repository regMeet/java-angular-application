package com.company.project.persistence.dao.interfaces;

import com.company.project.persistence.dao.interfaces.base.BaseDAO;
import com.company.project.persistence.entities.Users;
import com.google.common.base.Optional;

public interface UserDAO extends BaseDAO<Users> {

	Optional<Users> findByUsername(String username);

	Optional<Users> findByEmail(String email);

}