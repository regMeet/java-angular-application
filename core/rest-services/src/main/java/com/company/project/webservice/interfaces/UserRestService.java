package com.company.project.webservice.interfaces;

import com.company.project.persistence.entities.Users;
import com.company.project.webservice.interfaces.base.BaseRestService;

public interface UserRestService  extends BaseRestService<Users>{

	Users findByUserName(String name);

}
