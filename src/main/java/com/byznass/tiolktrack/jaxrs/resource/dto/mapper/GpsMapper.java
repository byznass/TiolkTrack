package com.byznass.tiolktrack.jaxrs.resource.dto.mapper;

import com.byznass.tiolktrack.jaxrs.resource.dto.GpsDto;
import com.byznass.tiolktrack.kernel.model.gps.Gps;

public class GpsMapper {

	public Gps toModel(GpsDto gpsDto, String userId) {

		return new Gps(userId, gpsDto.getGpsName());
	}

	public GpsDto toDto(Gps gps) {

		return new GpsDto(gps.getName());
	}

}
