package com.navent.spring.exceptions;

public class PokemonNotFoundException extends NoDataException {

	private static final long serialVersionUID = 1L;

	public PokemonNotFoundException() {
		super();
	}

	public PokemonNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PokemonNotFoundException(String message) {
		super(message);
	}

	
}
