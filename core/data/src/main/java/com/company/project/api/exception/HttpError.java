package com.company.project.api.exception;

public enum HttpError {
	BAD_REQUEST_HEADER("header.field.blank"),

	ACCOUNT_NOT_FOUND_FACEBOOK("facebook.account.not.found"),
	UNAUTHORIZED_FACEBOOK("facebook.unauthorized.access"),
	GENERIC_FACEBOOK_ERROR("facebook.api.error"),

	PROVIDER_CONFLICT_ACCOUNT("provider.conflict.account.already.used"),
	CONFLICT_ACCOUNT("project.conflict.account.already.used"),

	ACCOUNT_NOT_FOUND("project.user.account.not.found"),
	UNAUTHORIZED("project.unauthorized.access"),
	ACCOUNT_SUSPENDED("project.account.suspended"),
	ACCOUNT_NOT_VERIFIED("project.account.not.verified"),
	FORGET_PASSWORD_LINK_EXPIRED("project.account.forget.password.link.expired"),
	CONTACT_ADMIN("project.contact.administrator"),
	ACCOUNT_LINKED_NEEDED_PRECONDITION_FAILED_API("project.acount.linked.needed"),
	INTERNAL_ERROR("project.internal.error");

	private String messageKey;

	private HttpError(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}

}
