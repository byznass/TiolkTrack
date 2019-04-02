package com.byznass.tiolktrack.postgres;

public class ConnectionFailureException extends RuntimeException {

	public ConnectionFailureException(String message, Throwable cause) {

		super(message, cause);
	}
}
