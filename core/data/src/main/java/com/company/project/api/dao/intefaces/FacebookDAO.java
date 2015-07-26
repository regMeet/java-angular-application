package com.company.project.api.dao.intefaces;

import com.company.project.api.entities.facebook.FacebookUser;
import com.company.project.api.exception.HttpStatusException;

public interface FacebookDAO {

	public FacebookUser getMe(String accessToken) throws HttpStatusException;

}
