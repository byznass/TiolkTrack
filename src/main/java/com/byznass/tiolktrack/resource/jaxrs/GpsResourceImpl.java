package com.byznass.tiolktrack.resource.jaxrs;

import com.byznass.tiolktrack.kernel.handler.GpsLocationById;
import com.byznass.tiolktrack.resource.jaxrs.dto.Location;
import com.byznass.tiolktrack.resource.jaxrs.dto.mapper.LocationMapper;

import javax.inject.Inject;

public class GpsResourceImpl implements GpsResource {

	private final GpsLocationById gpsLocationById;
	private final LocationMapper locationMapper;

	@Inject
	GpsResourceImpl(GpsLocationById gpsLocationById, LocationMapper locationMapper) {

		this.gpsLocationById = gpsLocationById;
		this.locationMapper = locationMapper;
	}

	@Override
	public Location getLocationById(String gpsId) {

		com.byznass.tiolktrack.kernel.model.Location location = gpsLocationById.execute(gpsId);

		return locationMapper.toDto(location);
	}
}