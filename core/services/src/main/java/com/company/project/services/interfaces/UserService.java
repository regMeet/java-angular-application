package com.company.project.services.interfaces;

import com.company.project.persistence.entities.User;
import com.company.project.persistence.entities.User.Provider;
import com.company.project.services.interfaces.base.BaseService;
import com.google.common.base.Optional;

public interface UserService extends BaseService<User> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Optional<User> findByProvider(Provider provider, String providerId);

	User getCurrentUser();

}
