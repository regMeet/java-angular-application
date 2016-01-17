package com.company.project.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class HttpContentNotFoundException extends HttpStatusException {
	private static final long serialVersionUID = 9000519610559168248L;

	public HttpContentNotFoundException(HttpError httpError) {
		super(httpError);
	}

}