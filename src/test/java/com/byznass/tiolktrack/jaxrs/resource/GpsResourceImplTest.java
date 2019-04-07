package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.LocationMapper;
import com.byznass.tiolktrack.kernel.handler.GpsLocationById;
import com.byznass.tiolktrack.kernel.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GpsResourceImplTest {

	@Mock
	private GpsLocationById gpsLocationById;
	@Mock
	private LocationMapper locationMapper;

	private GpsResourceImpl gpsResource;

	@Before
	public void setUp() {

		initMocks(this);

		gpsResource = new GpsResourceImpl(gpsLocationById, locationMapper);
	}

	@Test
	public void whenGettingCurrentLocationOfGpsThenReturnCorrectResponse() {

		// setup
		String gpsId = "xyz";
		ZonedDateTime time = ZonedDateTime.of(2019, 3, 24, 22, 56, 23, 11, ZoneId.of("+02:00"));
		Location modelLocation =
				new Location("locid", "47.151154", "27.589897", time, gpsId);
		com.byznass.tiolktrack.jaxrs.resource.dto.Location expectedLocation =
				new com.byznass.tiolktrack.jaxrs.resource.dto.Location("47.151154", "27.589897", time.toString());

		when(gpsLocationById.execute(gpsId)).thenReturn(modelLocation);
		when(locationMapper.toDto(modelLocation)).thenReturn(expectedLocation);

		// execute
		com.byznass.tiolktrack.jaxrs.resource.dto.Location actualLocation = gpsResource.getLocationById(gpsId);

		// verify
		assertEquals(expectedLocation, actualLocation);
		verify(gpsLocationById).execute(gpsId);
		verify(locationMapper).toDto(modelLocation);
	}

	@Test(expected = RuntimeException.class)
	public void givenAnExceptionIsThrownWhenGettingCurrentLocationThenRethrow() {

		String gpsId = "123";

		when(gpsLocationById.execute(gpsId)).thenThrow(new RuntimeException());

		gpsResource.getLocationById(gpsId);
	}

}