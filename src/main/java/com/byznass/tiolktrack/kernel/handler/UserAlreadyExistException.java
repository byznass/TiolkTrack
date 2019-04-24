package com.byznass.tiolktrack.kernel.handler;

public class UserAlreadyExistException extends RuntimeException {

	public UserAlreadyExistException(String message) {

		super(message);
	}
}
