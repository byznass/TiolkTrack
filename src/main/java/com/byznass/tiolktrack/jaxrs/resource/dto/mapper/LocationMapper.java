package com.byznass.tiolktrack.jaxrs.resource.dto.mapper;

import com.byznass.tiolktrack.kernel.model.Location;

public class LocationMapper {

	public com.byznass.tiolktrack.jaxrs.resource.dto.Location toDto(Location location) {

		return new com.byznass.tiolktrack.jaxrs.resource.dto.Location(location.getLatitude(), location.getLongitude(), location.getTime().toString());
	}
}
