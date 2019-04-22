package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.Location;
import com.byznass.tiolktrack.jaxrs.resource.dto.LocationValidator;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.LocationMapper;
import com.byznass.tiolktrack.kernel.handler.GetGpsLocationHandler;
import com.byznass.tiolktrack.kernel.handler.PersistLocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class GpsResourceImpl implements GpsResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(GpsResourceImpl.class);

	private final GetGpsLocationHandler getGpsLocationHandler;
	private final PersistLocationHandler persistLocationHandler;
	private final LocationMapper locationMapper;
	private final LocationValidator locationValidator;

	@Inject
	GpsResourceImpl(GetGpsLocationHandler getGpsLocationHandler, PersistLocationHandler persistLocationHandler, LocationMapper locationMapper, LocationValidator locationValidator) {

		this.getGpsLocationHandler = getGpsLocationHandler;
		this.persistLocationHandler = persistLocationHandler;
		this.locationMapper = locationMapper;
		this.locationValidator = locationValidator;
	}

	@Override
	public Location getLocationById(String gpsId) {

		LOGGER.info("Request for last location for GPS with id=\'{}\'", gpsId);
		com.byznass.tiolktrack.kernel.model.Location location = getGpsLocationHandler.getLastLocation(gpsId);
		LOGGER.info("Returning last location of GPS with id=\'{}\'", gpsId);

		return locationMapper.toDto(location);
	}

	@Override
	public Location createLocationForGps(String gpsId, Location locationDto) {

		LOGGER.info("Request for creating a location for GPS with id=\'{}\'", gpsId);

		locationValidator.validate(locationDto);
		com.byznass.tiolktrack.kernel.model.Location locationModel = locationMapper.toModel(locationDto, gpsId);
		com.byznass.tiolktrack.kernel.model.Location result = persistLocationHandler.persist(locationModel);

		LOGGER.info("Created a location for GPS with id=\'{}\'", gpsId);

		return locationMapper.toDto(result);
	}
}