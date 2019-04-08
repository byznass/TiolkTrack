package com.byznass.tiolktrack.kernel.model;

import javax.inject.Singleton;
import java.time.ZonedDateTime;

@Singleton
public class Location {

	private final String latitude;
	private final String longitude;
	private final ZonedDateTime time;
	private final String gpsId;

	public Location(String latitude, String longitude, ZonedDateTime time, String gpsId) {

		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
		this.gpsId = gpsId;
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

	public String getGpsId() {

		return gpsId;
	}
}
