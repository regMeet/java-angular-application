package com.company.project.api.exception;

public class HttpStatusException extends Exception {
    private static final long serialVersionUID = -8688416654466199073L;
    private HttpError httpError;

    public HttpStatusException(HttpError httpError) {
        super();
        this.httpError = httpError;
    }

    public HttpStatusException(HttpError httpError, String message) {
        super(message);
        this.httpError = httpError;
    }

    public HttpStatusException(HttpError httpError, Throwable cause) {
        super(cause);
        this.httpError = httpError;
    }

    public HttpStatusException(HttpError httpError, String message, Throwable cause) {
        super(message, cause);
    }

    public HttpError getHttpError() {
        return httpError;
    }

    public String getMessageKey() {
        return httpError.getMessageKey();
    }
}
