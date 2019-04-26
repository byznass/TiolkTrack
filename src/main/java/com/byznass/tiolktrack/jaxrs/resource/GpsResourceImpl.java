package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.GpsDto;
import com.byznass.tiolktrack.jaxrs.resource.dto.Location;
import com.byznass.tiolktrack.jaxrs.resource.dto.LocationValidator;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.GpsMapper;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.LocationMapper;
import com.byznass.tiolktrack.kernel.handler.GetGpsLocationHandler;
import com.byznass.tiolktrack.kernel.handler.PersistGpsHandler;
import com.byznass.tiolktrack.kernel.handler.PersistLocationHandler;
import com.byznass.tiolktrack.kernel.model.gps.Gps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class GpsResourceImpl implements GpsResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(GpsResourceImpl.class);

	private final GetGpsLocationHandler getGpsLocationHandler;
	private final PersistLocationHandler persistLocationHandler;
	private final LocationMapper locationMapper;
	private final LocationValidator locationValidator;
	private final GpsMapper gpsMapper;
	private final PersistGpsHandler persistGpsHandler;

	@Inject
	GpsResourceImpl(GetGpsLocationHandler getGpsLocationHandler, PersistLocationHandler persistLocationHandler, LocationMapper locationMapper, LocationValidator locationValidator, GpsMapper gpsMapper, PersistGpsHandler persistGpsHandler) {

		this.getGpsLocationHandler = getGpsLocationHandler;
		this.persistLocationHandler = persistLocationHandler;
		this.locationMapper = locationMapper;
		this.locationValidator = locationValidator;
		this.gpsMapper = gpsMapper;
		this.persistGpsHandler = persistGpsHandler;
	}

	@Override
	public Location getLocation(String userId, String gpsName) {

		LOGGER.info("Request for last location for GPS entity (\'{}, {}\')", userId, gpsName);
		com.byznass.tiolktrack.kernel.model.Location location = getGpsLocationHandler.getLastLocation(userId, gpsName);
		LOGGER.info("Returning last location of GPS entity (\'{}, {}\')", userId, gpsName);

		return locationMapper.toDto(location);
	}

	@Override
	public Location createLocationForGps(String userId, String gpsName, Location locationDto) {

		LOGGER.info("Request for creating a location for GPS entity (\'{}, {}\')", userId, gpsName);

		locationValidator.validate(locationDto);
		com.byznass.tiolktrack.kernel.model.Location locationModel =
				locationMapper.toModel(locationDto, userId, gpsName);
		com.byznass.tiolktrack.kernel.model.Location result = persistLocationHandler.persist(locationModel);

		LOGGER.info("Created a location for GPS entity (\'{}, {}\')", userId, gpsName);

		return locationMapper.toDto(result);
	}

	@Override
	public GpsDto createGps(String userId, GpsDto gpsDto) {

		Gps gps = gpsMapper.toModel(gpsDto, userId);

		return gpsMapper.toDto(persistGpsHandler.persistGps(gps));
	}
}