package com.company.project.api.exception;

public enum HttpError {
    BAD_REQUEST_HEADER("header.field.blank"),

    ACCOUNT_NOT_FOUND_MERCADO_LIBRE("mercadolibre.account.not.found"),
    PUBLICATIONS_NOT_FOUND_MERCADO_LIBRE("mercadolibre.publications.not.found"),

    PAYMENT_METHOD_PRECONDITION_FAILED_FACEBOOK("facebook.payment.method.not.found"),

    ACCOUNT_NOT_FOUND_FACEBOOK("facebook.account.not.found"),

    UNAUTHORIZED_MERCADO_LIBRE("mercadolibre.unauthorized.access"),
    INVALID_TOKEN_MERCADO_LIBRE("mercadolibre.invalid.access.token"),
    UNAUTHORIZED_FACEBOOK("facebook.unauthorized.access"),
    EXPIRED_SESSION_FACEBOOK("facebook.expired.access.token"),
    INVALID_TOKEN_FACEBOOK("facebook.invalid.access.token"),

    CONFLICT_FACEBOOK("facebook.conflict.account.already.used"),

    GENERIC_FACEBOOK_ERROR("facebook.api.error"),
    GENERIC_MERCADO_LIBRE_ERROR("mercadolibre.api.error");

    private String messageKey;

    private HttpError(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

}
