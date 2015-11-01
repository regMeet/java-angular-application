package com.company.project.webservice.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.company.project.VO.ErrorVO;
import com.company.project.VO.ValidationErrorVO;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpConflictException;
import com.company.project.api.exception.HttpContentNotFoundException;
import com.company.project.api.exception.HttpError;
import com.company.project.api.exception.HttpPreconditionFailedException;
import com.company.project.api.exception.HttpStatusException;

/**
 * Handles possibles exceptions that may throw spring rest services.
 */
@ControllerAdvice
public class ExceptionController {
	private final static Logger log = Logger.getLogger(ExceptionController.class);

	@ExceptionHandler({ HttpAuthenticationException.class })
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public @ResponseBody ErrorVO authenticateAccessToken(HttpStatusException e) {
		return createErrorVO(e);
	}

	@ExceptionHandler({ HttpConflictException.class })
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public @ResponseBody ErrorVO conflictExistentAccount(HttpStatusException e) {
		return createErrorVO(e);
	}

	@ExceptionHandler({ HttpContentNotFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorVO accountNotFound(HttpStatusException e) {
		return createErrorVO(e);
	}

	@ExceptionHandler({ HttpPreconditionFailedException.class })
	@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
	public @ResponseBody ErrorVO preconditionFailed(HttpStatusException e) {
		return createErrorVO(e);
	}

	private ErrorVO createErrorVO(HttpStatusException e) {
		ErrorVO error = new ErrorVO();
		error.setMessage(e.getMessageKey());
		return error;
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody List<ValidationErrorVO> validations(MethodArgumentNotValidException e) {
		List<ValidationErrorVO> errorList = new ArrayList<ValidationErrorVO>();

		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			ValidationErrorVO error = new ValidationErrorVO();
			error.setField(fieldError.getField());
			error.setMessage(fieldError.getDefaultMessage());
			errorList.add(error);
		}
		return errorList;
	}

	@ExceptionHandler({ ServletRequestBindingException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorVO requestBinding(ServletRequestBindingException e) {
		ErrorVO error = new ErrorVO();
		error.setMessage(HttpError.BAD_REQUEST_HEADER.getMessageKey());
		return error;
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorVO exception(Exception e) {
		log.error("An exception has ocurred", e);
		e.printStackTrace();
		ErrorVO error = new ErrorVO();
		error.setMessage(e.getMessage());
		return error;
	}

}