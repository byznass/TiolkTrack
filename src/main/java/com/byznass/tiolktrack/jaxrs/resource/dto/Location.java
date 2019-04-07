package com.byznass.tiolktrack.jaxrs.resource.dto;

import java.util.Objects;

public class Location {

	private static final String TO_STRING_FORMAT =
			"{\"latitude\": \"%s\", \"longitude\": \"%s\", \"lastUpdate\": \"%s\"}";
	private static final String ALL_PARAMETERS_MUST_BE_NON_NULL = "All parameters must be non-null";

	private String latitude;
	private String longitude;
	private String time;

	public Location(String latitude, String longitude, String time) {

		if (latitude == null || longitude == null || time == null) {
			throw new IllegalArgumentException(ALL_PARAMETERS_MUST_BE_NON_NULL);
		}

		this.longitude = longitude;
		this.latitude = latitude;
		this.time = time;
	}

	public String getLatitude() {

		return latitude;
	}

	public void setLatitude(String latitude) {

		if (latitude == null) {
			throw new IllegalArgumentException(ALL_PARAMETERS_MUST_BE_NON_NULL);
		}

		this.latitude = latitude;
	}

	public String getLongitude() {

		return longitude;
	}

	public void setLongitude(String longitude) {

		if (longitude == null) {
			throw new IllegalArgumentException(ALL_PARAMETERS_MUST_BE_NON_NULL);
		}

		this.longitude = longitude;
	}

	@Override
	public String toString() {

		return String.format(TO_STRING_FORMAT, latitude, longitude, time);
	}

	public String getTime() {

		return time;
	}

	public void setTime(String time) {

		if (time == null) {
			throw new IllegalArgumentException(ALL_PARAMETERS_MUST_BE_NON_NULL);
		}

		this.time = time;
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
				time.equals(location.time);
	}

	@Override
	public int hashCode() {

		return Objects.hash(latitude, longitude, time);
	}
}
