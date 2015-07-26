package com.company.project.api.exception;

public enum HttpError {
    BAD_REQUEST_HEADER("header.field.blank"),

    PAYMENT_METHOD_PRECONDITION_FAILED_FACEBOOK("facebook.payment.method.not.found"),
    ACCOUNT_NOT_FOUND_FACEBOOK("facebook.account.not.found"),
    UNAUTHORIZED_FACEBOOK("facebook.unauthorized.access"),
    EXPIRED_SESSION_FACEBOOK("facebook.expired.access.token"),
    INVALID_TOKEN_FACEBOOK("facebook.invalid.access.token"),

    CONFLICT_ACCOUNT("link.conflict.account.already.used"),

    GENERIC_FACEBOOK_ERROR("facebook.api.error"),

    ACCOUNT_NOT_FOUND_API("user.account.not.found"),
    UNAUTHORIZED_API("project.unauthorized.access");

    private String messageKey;

    private HttpError(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

}
