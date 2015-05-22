package com.company.project.services.interfaces;

import com.company.project.persistence.entities.Users;
import com.company.project.services.interfaces.base.BaseService;

public interface UserService  extends BaseService<Users>{

	Users findByUserName(String name);

}
