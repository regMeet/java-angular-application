package com.company.project.services.interfaces;

import com.company.project.persistence.entities.Users;
import com.company.project.persistence.entities.Users.Provider;
import com.company.project.services.interfaces.base.BaseService;
import com.google.common.base.Optional;

public interface UserService extends BaseService<Users> {

	Optional<Users> findByUsername(String username);

	Optional<Users> findByEmail(String email);

	Optional<Users> findByProvider(Provider provider, String providerId);

}
