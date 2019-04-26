package com.byznass.tiolktrack.jaxrs.resource.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class GpsDto {
	private final String gpsName;

	@JsonCreator
	public GpsDto(@JsonProperty("name") String gpsName) {

		if(gpsName == null){
			throw new InvalidDtoException("Gps name cannot be null.");
		}

		this.gpsName = gpsName;
	}

	public String getGpsName() {

		return gpsName;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof GpsDto))
			return false;
		GpsDto gpsDto = (GpsDto) o;
		return gpsName.equals(gpsDto.gpsName);
	}

	@Override
	public int hashCode() {

		return Objects.hash(gpsName);
	}
}
