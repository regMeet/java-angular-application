package com.company.project.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class HttpFailedDependencyException extends HttpStatusException {
	private static final long serialVersionUID = -3851723456772637045L;

	public HttpFailedDependencyException(HttpError httpError) {
		super(httpError);
	}

}