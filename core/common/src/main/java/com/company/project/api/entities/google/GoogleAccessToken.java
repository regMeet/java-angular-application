package com.company.project.api.entities.google;

import com.google.gson.annotations.SerializedName;

public class GoogleAccessToken {

	private @SerializedName("access_token") String accessToken;
	private @SerializedName("token_type") String tokenType;
	private @SerializedName("expires_in") String expiresIn;
	private @SerializedName("id_token") String idToken;

	public GoogleAccessToken() {
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

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

}
