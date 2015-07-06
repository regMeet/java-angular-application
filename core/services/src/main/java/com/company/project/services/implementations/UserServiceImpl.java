package com.company.project.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.persistence.dao.interfaces.UserDAO;
import com.company.project.persistence.entities.User;
import com.company.project.persistence.entities.User.Provider;
import com.company.project.services.implementations.base.BaseServiceImpl;
import com.company.project.services.interfaces.UserService;
import com.google.common.base.Optional;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserDAO> implements UserService {

	@Autowired
	public UserServiceImpl(UserDAO baseDao) {
		super(baseDao);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return baseDao.findByUsername(username);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return baseDao.findByEmail(email);
	}

	@Override
	public Optional<User> findByProvider(Provider provider, String providerId) {
		return baseDao.findByProvider(provider, providerId);
	}
}
