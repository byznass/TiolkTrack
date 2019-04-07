package com.byznass.tiolktrack.jaxrs.resource.dto.mapper;

import com.byznass.tiolktrack.jaxrs.resource.dto.Location;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class LocationMapperTest {

	private LocationMapper locationMapper;

	@Before
	public void setUp() {

		locationMapper = new LocationMapper();
	}

	@Test
	public void givenModelLocationWhenMappingToDotThenDoItCorrectly() {

		ZonedDateTime time = ZonedDateTime.of(2019, 4, 6, 10, 15, 20, 0, ZoneId.of("+02:00"));
		String gpsID = "xxx";
		com.byznass.tiolktrack.kernel.model.Location modelLocation =
				new com.byznass.tiolktrack.kernel.model.Location("locid", "123", "456", time, gpsID);

		Location actualLocation = locationMapper.toDto(modelLocation);

		Location expectedLocation = new Location("123", "456", "2019-04-06T10:15:20+02:00");
		assertEquals(expectedLocation, actualLocation);
	}
}