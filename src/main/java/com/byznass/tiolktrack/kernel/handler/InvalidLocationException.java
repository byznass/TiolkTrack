package com.byznass.tiolktrack.kernel.handler;

public class InvalidLocationException extends RuntimeException {

	private final Reason reason;

	public enum Reason {
		INVALID_LATITUDE("Given Latitude has invalid format. Valid format: a degree with value between -90 and +90"),
		INVALID_LONGITUDE("Given Longitude has invalid format. Valid format: a degree with value between -180 and +180");

		private String message;

		Reason(String message) {

			this.message = message;
		}

	}

	public InvalidLocationException(Reason reason) {

		super(reason.message);
		this.reason = reason;
	}

	public Reason getReason() {

		return reason;
	}
}
