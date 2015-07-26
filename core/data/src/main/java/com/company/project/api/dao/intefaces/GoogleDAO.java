package com.company.project.api.dao.intefaces;

import com.company.project.api.entities.google.GoogleAccessToken;
import com.company.project.api.entities.google.GoogleUser;
import com.company.project.api.exception.HttpStatusException;

public interface GoogleDAO {

	public GoogleAccessToken getAccessTokenFromCode(String code, String clientId, String redirectUri) throws HttpStatusException;

	public GoogleUser getMe(String accessToken) throws HttpStatusException;

}
