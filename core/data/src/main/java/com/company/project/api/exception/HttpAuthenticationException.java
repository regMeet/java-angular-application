package com.company.project.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class HttpAuthenticationException extends HttpStatusException {
    private static final long serialVersionUID = -1590899480004837656L;

    public HttpAuthenticationException(HttpError httpError) {
        super(httpError);
    }

    public HttpAuthenticationException(HttpError httpError, String message) {
        super(httpError, message);
    }

    public HttpAuthenticationException(HttpError httpError, Throwable cause) {
        super(httpError, cause);
    }

    public HttpAuthenticationException(HttpError httpError, String message, Throwable cause) {
        super(httpError, message, cause);
    }
}