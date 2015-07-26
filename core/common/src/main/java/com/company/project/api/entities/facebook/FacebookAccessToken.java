package com.company.project.api.entities.facebook;

import com.company.project.api.entities.facebook.base.FacebookEntity;
import com.google.gson.annotations.SerializedName;

public class FacebookAccessToken extends FacebookEntity {

	private @SerializedName("access_token") String accessToken;
	private @SerializedName("token_type") String tokenType;
	private @SerializedName("expires_in") String expiresIn;

	public FacebookAccessToken() {
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

}
