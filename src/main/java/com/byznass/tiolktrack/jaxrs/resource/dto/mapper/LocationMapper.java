package com.byznass.tiolktrack.jaxrs.resource.dto.mapper;

import com.byznass.tiolktrack.jaxrs.resource.dto.Location;

public class LocationMapper {

	public Location toDto(com.byznass.tiolktrack.kernel.model.Location modelLocation) {

		return new Location(modelLocation.getLatitude(), modelLocation.getLongitude(), modelLocation.getTime().toString());
	}
}
