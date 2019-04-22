package com.byznass.tiolktrack.kernel.handler;

public class NoLocationForGpsException extends RuntimeException {

	private static final String ERROR_MESSAGE = "GPS with id=\'%s\' has no location";

	private final String gpsId;

	public NoLocationForGpsException(String gpsId) {

		this.gpsId = gpsId;
	}

	@Override
	public String getMessage() {

		return String.format(ERROR_MESSAGE, gpsId);
	}
}
