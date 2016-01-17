package com.company.project.api.exception;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class HttpPreconditionFailedException extends HttpStatusException {
    private static final long serialVersionUID = -1125189651208710568L;
    private Set<String> fields;

    public HttpPreconditionFailedException(Set<String> fields, HttpError httpError) {
        super(httpError);
        this.fields = fields;
    }

    public HttpPreconditionFailedException(HttpError httpError) {
        super(httpError);
    }

    public Set<String> getFields() {
        return fields;
    }
}