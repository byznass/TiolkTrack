package com.byznass.tiolktrack.kernel.util;

public class IdentifierValidator {

	public static final int MAX_LENGTH = 50;
	public static final String CHAR_SET = "latin letters, digits, underscore, dash or dot characters";

	public static boolean validate(String identifier) {

		return identifier.matches("^[a-zA-Z0-9_.\\-]+") && identifier.length() <= MAX_LENGTH;
	}
}
