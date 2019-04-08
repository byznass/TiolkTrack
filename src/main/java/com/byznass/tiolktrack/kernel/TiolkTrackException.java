package com.byznass.tiolktrack.kernel;

/**
 * Base expcetion class.
 */
public class TiolkTrackException extends RuntimeException {

	public TiolkTrackException(String message, Throwable reason) {

		super(message, reason);
	}

	public TiolkTrackException(String message) {

		super(message);
	}
}
