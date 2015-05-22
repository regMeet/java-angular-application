package com.company.project.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.persistence.dao.interfaces.UserDAO;
import com.company.project.persistence.entities.Users;
import com.company.project.services.implementations.base.BaseServiceImpl;
import com.company.project.services.interfaces.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<Users, UserDAO> implements UserService {
	
	@Autowired
	public UserServiceImpl(UserDAO baseDao) {
		super(baseDao);
	}

	@Override
	public Users findByUserName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
