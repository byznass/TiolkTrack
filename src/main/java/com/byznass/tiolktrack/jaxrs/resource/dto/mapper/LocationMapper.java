package com.byznass.tiolktrack.jaxrs.resource.dto.mapper;

import com.byznass.tiolktrack.config.TimeProvider;
import com.byznass.tiolktrack.jaxrs.resource.dto.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.LocalDateTime;

public class LocationMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationMapper.class);

	private final TimeProvider timeProvider;

	@Inject
	public LocationMapper(TimeProvider timeProvider) {

		this.timeProvider = timeProvider;
	}

	public Location toDto(com.byznass.tiolktrack.kernel.model.Location modelLocation) {

		return new Location(modelLocation.getLatitude(), modelLocation.getLongitude(), modelLocation.getTime().toString());
	}

	public com.byznass.tiolktrack.kernel.model.Location toModel(Location location, String userId, String gpsName) {

		LocalDateTime time = timeProvider.getCurrentTime();
		LOGGER.info("Mapping location from dto to model using time = '{}'", time);

		return new com.byznass.tiolktrack.kernel.model.Location(location.getLatitude(), location.getLongitude(), time, userId, gpsName);
	}
}
