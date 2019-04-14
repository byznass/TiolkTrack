package com.byznass.tiolktrack.jaxrs.filter;

public class AuthenticationException extends RuntimeException {

	public enum Reason {

		INVALID_AUTHENTICATION_METHOD("Provided authentication method is invalid. Only Bearer auth method is accepted"),
		INVALID_FORMAT("Provided format for token is invalid. Correct format: 'Bearer <userId> <token>'"),
		INVALID_TOKEN("Provided token is invalid");

		private String message;

		Reason(String message) {

			this.message = message;
		}

		public String getMessage() {

			return message;
		}

	}

	private final Reason reason;

	public AuthenticationException(Reason reason) {

		super(reason.getMessage());

		this.reason = reason;
	}

	public Reason getReason() {

		return reason;
	}

}
