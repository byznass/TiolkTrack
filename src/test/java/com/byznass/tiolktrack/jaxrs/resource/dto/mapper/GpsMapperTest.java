package com.byznass.tiolktrack.jaxrs.resource.dto.mapper;

import com.byznass.tiolktrack.jaxrs.resource.dto.GpsDto;
import com.byznass.tiolktrack.kernel.model.gps.Gps;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GpsMapperTest {

	private GpsMapper gpsMapper;

	@Before
	public void setUp() {

		gpsMapper = new GpsMapper();
	}

	@Test
	public void givenGpsDtoWhenMappingToModelThenReturnCorrectResult() {

		GpsDto gpsDto = new GpsDto("name");
		String userId = "user";

		Gps actualGps = gpsMapper.toModel(gpsDto, userId);

		assertEquals(userId, actualGps.getUserId());
		assertEquals("name", actualGps.getName());
	}

	@Test
	public void givenGpsWhenMappingToDtoThenReturnCorrectResult() {

		Gps gps = new Gps("user", "name");

		GpsDto actualGpsDto = gpsMapper.toDto(gps);

		assertEquals("name", actualGpsDto.getGpsName());
	}
}