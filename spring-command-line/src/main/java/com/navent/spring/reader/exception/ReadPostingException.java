package com.navent.spring.reader.exception;

public class ReadPostingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReadPostingException() {
		super();
	}

	public ReadPostingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReadPostingException(String message) {
		super(message);
	}

	
}
