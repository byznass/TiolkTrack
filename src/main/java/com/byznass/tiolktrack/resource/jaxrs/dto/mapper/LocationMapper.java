package com.byznass.tiolktrack.resource.jaxrs.dto.mapper;

import com.byznass.tiolktrack.kernel.model.Location;

public class LocationMapper {

	public com.byznass.tiolktrack.resource.jaxrs.dto.Location toDto(Location location) {

		return new com.byznass.tiolktrack.resource.jaxrs.dto.Location(location.getLatitude(), location.getLongitude(), location.getTime().toString());
	}
}
