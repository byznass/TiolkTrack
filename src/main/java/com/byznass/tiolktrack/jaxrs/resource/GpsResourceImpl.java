package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.Location;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.LocationMapper;
import com.byznass.tiolktrack.kernel.handler.GpsLocationById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class GpsResourceImpl implements GpsResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(GpsResourceImpl.class);

	private final GpsLocationById gpsLocationById;
	private final LocationMapper locationMapper;

	@Inject
	GpsResourceImpl(GpsLocationById gpsLocationById, LocationMapper locationMapper) {

		this.gpsLocationById = gpsLocationById;
		this.locationMapper = locationMapper;
	}

	@Override
	public Location getLocationById(String gpsId) {

		LOGGER.info("Request for last location for GPS with id=\"{}\"", gpsId);
		com.byznass.tiolktrack.kernel.model.Location location = gpsLocationById.execute(gpsId);
		LOGGER.info("Returning last location of GPS with id=\"{}\"", gpsId);

		return locationMapper.toDto(location);
	}
}