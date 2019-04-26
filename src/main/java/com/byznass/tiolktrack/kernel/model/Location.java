package com.byznass.tiolktrack.kernel.model;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Objects;

@Singleton
public class Location {

	private static final String ALL_PARAMETERS_MUST_BE_NON_NULL = "All parameters must be non-null";

	private final String latitude;
	private final String longitude;
	private final LocalDateTime time;
	private final String userId;
	private final String gpsName;

	public Location(String latitude, String longitude, LocalDateTime time, String userId, String gpsName) {

		if (latitude == null || longitude == null || time == null || userId == null || gpsName == null) {
			throw new IllegalArgumentException(ALL_PARAMETERS_MUST_BE_NON_NULL);
		}

		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
		this.userId = userId;
		this.gpsName = gpsName;
	}

	public String getLongitude() {

		return longitude;
	}

	public String getLatitude() {

		return latitude;
	}

	public LocalDateTime getTime() {

		return time;
	}

	public String getUserId() {

		return userId;
	}

	public String getGpsName() {

		return gpsName;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}

		if (!(o instanceof Location)) {
			return false;
		}

		Location location = (Location) o;

		return latitude.equals(location.latitude) &&
				longitude.equals(location.longitude) &&
				time.equals(location.time) &&
				userId.equals(location.userId) &&
				gpsName.equals(location.gpsName);
	}

	@Override
	public int hashCode() {

		return Objects.hash(latitude, longitude, time, userId, gpsName);
	}
}
