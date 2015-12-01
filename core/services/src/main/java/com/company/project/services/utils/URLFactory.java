package com.company.project.services.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class URLFactory {

	@Value("${application.uri}")
	private String urlApp;

	private final String VERIFY_URL = "/#/verify?token=";
	private final String FORGOT_PASS_URL = "/#/forgot-password?token=";

	public String getVerifyUrl() {
		return urlApp + VERIFY_URL;
	}

	public String getForgotPassUrl() {
		return urlApp + FORGOT_PASS_URL;
	}

	public String getCreatedResourceUrl(Integer resourceId) {
		return urlApp + "/" + resourceId;
	}

}
