package com.byznass.tiolktrack.kernel.handler;

public class InvalidUserIdException extends RuntimeException {

	public InvalidUserIdException(String message) {

		super(message);
	}
}
