package com.company.project.api.exception;

public enum HttpError {
    BAD_REQUEST_HEADER("header.field.blank"),

    ACCOUNT_NOT_FOUND_FACEBOOK("facebook.account.not.found"),
    UNAUTHORIZED_FACEBOOK("facebook.unauthorized.access"),
    EXPIRED_SESSION_FACEBOOK("facebook.expired.access.token"),
    INVALID_TOKEN_FACEBOOK("facebook.invalid.access.token"),
    GENERIC_FACEBOOK_ERROR("facebook.api.error"),

    PROVIDER_CONFLICT_ACCOUNT("provider.conflict.account.already.used"),
    CONFLICT_ACCOUNT("project.conflict.account.already.used"),

    ACCOUNT_NOT_FOUND_API("project.user.account.not.found"),
    UNAUTHORIZED_API("project.unauthorized.access"),
    CONTACT_ADMIN_API("project.contact.administrator"),
    ACCOUNT_LINKED_NEEDED_PRECONDITION_FAILED_API("project.acount.linked.needed");

    private String messageKey;

    private HttpError(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

}
