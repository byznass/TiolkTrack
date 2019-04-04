package com.byznass.tiolktrack.postgres;

public class LiquibaseUpdateException extends RuntimeException {

	public LiquibaseUpdateException(String message, Throwable cause) {

		super(message, cause);
	}
}
