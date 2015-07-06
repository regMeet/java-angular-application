package com.company.project.persistence.dao.implementations.exceptions;

public class NonExistentEntityException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
    public NonExistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonExistentEntityException(String message) {
        super(message);
    }
}
