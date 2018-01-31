package com.navent.spring.exceptions;

/**
 * @author socrates-clinis
 *
 */
public class NoDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoDataException() {
		super();
	}

	public NoDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoDataException(String message) {
		super(message);
	}
	
}
