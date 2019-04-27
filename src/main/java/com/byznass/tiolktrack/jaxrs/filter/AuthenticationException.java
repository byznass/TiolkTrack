package com.byznass.tiolktrack.jaxrs.filter;

public class AuthenticationException extends RuntimeException {

	public enum Reason {

		INVALID_AUTHENTICATION_METHOD("Provided authentication method is invalid. Only Basic auth method is accepted"),
		INVALID_FORMAT("Provided format is invalid. Correct format: 'Basic Base64(<userId>:<password>)'"),
		INVALID_TOKEN("Provided <userId password> pair is invalid");

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

	public AuthenticationException(Reason reason, Throwable cause) {

		super(reason.getMessage(), cause);

		this.reason = reason;
	}

	public Reason getReason() {

		return reason;
	}

}
