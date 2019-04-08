package com.byznass.tiolktrack.jaxrs.resource.dto.mapper;

import com.byznass.tiolktrack.jaxrs.resource.dto.Location;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocationMapper {

	public Location toDto(com.byznass.tiolktrack.kernel.model.Location modelLocation) {

		return new Location(modelLocation.getLatitude(), modelLocation.getLongitude(), modelLocation.getTime().toString());
	}

	public com.byznass.tiolktrack.kernel.model.Location toModel(Location location, String gpsId) {

		ZonedDateTime time = LocalDateTime.parse(location.getTime()).atZone(ZoneId.of("UTC"));

		return new com.byznass.tiolktrack.kernel.model.Location(location.getLatitude(), location.getLongitude(), time, gpsId);
	}
}
