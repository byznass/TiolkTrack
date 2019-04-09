package com.byznass.tiolktrack.jaxrs.resource.dto.mapper;

import com.byznass.tiolktrack.config.TimeProvider;
import com.byznass.tiolktrack.jaxrs.resource.dto.Location;

import javax.inject.Inject;
import java.time.LocalDateTime;

public class LocationMapper {

	private TimeProvider timeProvider;

	@Inject
	public LocationMapper(TimeProvider timeProvider) {

		this.timeProvider = timeProvider;
	}

	public Location toDto(com.byznass.tiolktrack.kernel.model.Location modelLocation) {

		return new Location(modelLocation.getLatitude(), modelLocation.getLongitude(), modelLocation.getTime().toString());
	}

	public com.byznass.tiolktrack.kernel.model.Location toModel(Location location, String gpsId) {

		LocalDateTime time = timeProvider.getCurrentTime();

		return new com.byznass.tiolktrack.kernel.model.Location(location.getLatitude(), location.getLongitude(), time, gpsId);
	}
}
