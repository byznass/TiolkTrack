package com.byznass.tiolktrack.kernel.handler;

public class NoLocationForGpsException extends RuntimeException {

	private static final String ERROR_MESSAGE = "GPS entity (\'%s, %s\') has no location";

	private final String userId;
	private final String gpsName;

	public NoLocationForGpsException(String userId, String gpsName) {

		this.userId = userId;

		this.gpsName = gpsName;
	}

	@Override
	public String getMessage() {

		return String.format(ERROR_MESSAGE, userId, gpsName);
	}
}
