package com.hkprogrammer.api.core.exception;

public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GenericException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenericException(String message) {
		super(message);
	}
	
}
