package com.byznass.tiolktrack.resource.jaxrs;

import com.byznass.tiolktrack.kernel.handler.GpsLocationById;
import com.byznass.tiolktrack.kernel.model.Location;
import com.byznass.tiolktrack.resource.jaxrs.dto.mapper.LocationMapper;
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
				new Location("47.151154", "27.589897", time);
		com.byznass.tiolktrack.resource.jaxrs.dto.Location expectedLocation =
				new com.byznass.tiolktrack.resource.jaxrs.dto.Location("47.151154", "27.589897", time.toString());

		when(gpsLocationById.execute(gpsId)).thenReturn(modelLocation);
		when(locationMapper.toDto(modelLocation)).thenReturn(expectedLocation);

		// execute
		com.byznass.tiolktrack.resource.jaxrs.dto.Location actualLocation = gpsResource.getLocationById(gpsId);

		// verify
		assertEquals(expectedLocation, actualLocation);
		verify(gpsLocationById).execute(gpsId);
		verify(locationMapper).toDto(modelLocation);
	}

}