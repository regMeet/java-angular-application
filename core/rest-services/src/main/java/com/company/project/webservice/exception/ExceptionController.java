package com.company.project.webservice.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.company.project.VO.errors.PreconditionFailedErrorVO;
import com.company.project.VO.errors.SimpleError;
import com.company.project.VO.errors.ValidationErrorVO;
import com.company.project.VO.errors.ValidationErrorVO.ValidationErrorDataVO;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpConflictException;
import com.company.project.api.exception.HttpContentNotFoundException;
import com.company.project.api.exception.HttpError;
import com.company.project.api.exception.HttpFailedDependencyException;
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
	public @ResponseBody SimpleError authenticateAccessToken(HttpStatusException e) {
		return createErrorVO(e);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public @ResponseBody SimpleError accessDenied() {
		SimpleError error = new SimpleError();
		error.setMessage(HttpError.UNAUTHORIZED.getMessageKey());
		return error;
	}

	@ExceptionHandler({ HttpConflictException.class })
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public @ResponseBody SimpleError conflictExistentAccount(HttpStatusException e) {
		return createErrorVO(e);
	}

	@ExceptionHandler({ HttpContentNotFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody SimpleError accountNotFound(HttpStatusException e) {
		return createErrorVO(e);
	}

	 @ExceptionHandler({ HttpPreconditionFailedException.class })
	    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
	    public @ResponseBody PreconditionFailedErrorVO preconditionFailed(HttpPreconditionFailedException e) {
	        PreconditionFailedErrorVO error = new PreconditionFailedErrorVO();
	        error.setMessage(e.getMessage());

	        if (e.getFields() != null) {
	            List<String> fields = new ArrayList<String>();
	            for (String field : e.getFields()) {
	                fields.add(field);
	            }
	            error.setFields(fields);
	        }

	        return error;
	    }

	@ExceptionHandler({ HttpFailedDependencyException.class })
	@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY)
	public @ResponseBody SimpleError failedDependency(HttpStatusException e) {
		return createErrorVO(e);
	}

    private SimpleError createErrorVO(HttpStatusException e) {
        SimpleError error = new SimpleError();
        error.setMessage(e.getMessage());
        return error;
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ValidationErrorVO validations(MethodArgumentNotValidException e) {
        ValidationErrorVO validationErrorVO = new ValidationErrorVO();

        List<ValidationErrorDataVO> errorList = new ArrayList<ValidationErrorDataVO>();

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            ValidationErrorDataVO error = new ValidationErrorDataVO();
            error.setField(fieldError.getField());
            error.setMessage(fieldError.getDefaultMessage());
            errorList.add(error);
        }

        ObjectError globalError = e.getBindingResult().getGlobalError();
        if (globalError != null) {
            ValidationErrorDataVO error = new ValidationErrorDataVO();
            error.setField(globalError.getObjectName());
            error.setMessage(globalError.getDefaultMessage());
            errorList.add(error);
        }

        validationErrorVO.setData(errorList);
        return validationErrorVO;
    }

	@ExceptionHandler({ ServletRequestBindingException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody SimpleError requestBinding(ServletRequestBindingException e) {
		SimpleError error = new SimpleError();
		error.setMessage(HttpError.BAD_REQUEST_HEADER.getMessageKey());
		return error;
	}

	@ExceptionHandler({ TransactionException.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody SimpleError requestBinding(TransactionException e) {
		log.error("An exception has ocurred", e);
		SimpleError error = new SimpleError();
		error.setMessage(HttpError.INTERNAL_ERROR.getMessageKey());
		return error;
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody SimpleError exception(Exception e) {
		log.error("An exception has ocurred", e);
		e.printStackTrace();
		SimpleError error = new SimpleError();
		error.setMessage(e.getMessage());
		return error;
	}

}