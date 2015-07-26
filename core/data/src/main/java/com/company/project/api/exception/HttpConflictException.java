package com.company.project.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class HttpConflictException extends HttpStatusException {
    private static final long serialVersionUID = -1590899480004837656L;

    public HttpConflictException(HttpError httpError) {
        super(httpError);
    }

    public HttpConflictException(HttpError httpError, String message) {
        super(httpError, message);
    }

    public HttpConflictException(HttpError httpError, Throwable cause) {
        super(httpError, cause);
    }

    public HttpConflictException(HttpError httpError, String message, Throwable cause) {
        super(httpError, message, cause);
    }
}