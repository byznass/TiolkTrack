package com.byznass.tiolktrack.jaxrs.resource.dto;

public class InvalidDtoException extends RuntimeException {

	public InvalidDtoException(String message) {

		super(message);
	}
}
