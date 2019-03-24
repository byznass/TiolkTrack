package com.byznass.tiolktrack.kernel.model;

import java.time.ZonedDateTime;

public class Location {

	private final String latitude;
	private final String longitude;
	private final ZonedDateTime time;

	public Location(String latitude, String longitude, ZonedDateTime time) {

		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
	}

	public String getLongitude() {

		return longitude;
	}

	public String getLatitude() {

		return latitude;
	}

	public ZonedDateTime getTime() {

		return time;
	}
}
