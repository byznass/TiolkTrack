package com.byznass.tiolktrack.kernel.util;

public class IdentifierValidator {

	public static boolean validate(String identifier) {

		return identifier.matches("^[a-zA-Z0-9_.\\-]+");
	}
}
