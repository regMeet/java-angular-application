package com.company.project.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class HttpPreconditionFailedException extends HttpStatusException {
    private static final long serialVersionUID = -1125189651208710568L;

    public HttpPreconditionFailedException(HttpError httpError) {
        super(httpError);
    }

    public HttpPreconditionFailedException(HttpError httpError, String message) {
        super(httpError, message);
    }

    public HttpPreconditionFailedException(HttpError httpError, Throwable cause) {
        super(httpError, cause);
    }

    public HttpPreconditionFailedException(HttpError httpError, String message, Throwable cause) {
        super(httpError, message, cause);
    }
}