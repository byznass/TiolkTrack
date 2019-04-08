package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.LocationMapper;
import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.handler.GetGpsLocationHandler;
import com.byznass.tiolktrack.kernel.handler.PersistLocationHandler;
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
	private GetGpsLocationHandler getGpsLocationHandler;
	@Mock
	private LocationMapper locationMapper;
	@Mock
	private PersistLocationHandler persistLocationHandler;

	private GpsResourceImpl gpsResource;

	@Before
	public void setUp() {

		initMocks(this);

		gpsResource = new GpsResourceImpl(getGpsLocationHandler, persistLocationHandler, locationMapper);
	}

	@Test
	public void whenGettingCurrentLocationOfGpsThenReturnCorrectResponse() {

		// setup
		String gpsId = "xyz";
		ZonedDateTime time = ZonedDateTime.of(2019, 3, 24, 22, 56, 23, 11, ZoneId.of("UTC"));
		Location modelLocation =
				new Location("47.151154", "27.589897", time, gpsId);
		com.byznass.tiolktrack.jaxrs.resource.dto.Location expectedLocation =
				new com.byznass.tiolktrack.jaxrs.resource.dto.Location("47.151154", "27.589897", time.toString());

		when(getGpsLocationHandler.getLastLocation(gpsId)).thenReturn(modelLocation);
		when(locationMapper.toDto(modelLocation)).thenReturn(expectedLocation);

		// execute
		com.byznass.tiolktrack.jaxrs.resource.dto.Location actualLocation = gpsResource.getLocationById(gpsId);

		// verify
		assertEquals(expectedLocation, actualLocation);
		verify(getGpsLocationHandler).getLastLocation(gpsId);
		verify(locationMapper).toDto(modelLocation);
	}

	@Test(expected = RuntimeException.class)
	public void givenAnExceptionIsThrownWhenGettingCurrentLocationThenRethrow() {

		String gpsId = "123";

		when(getGpsLocationHandler.getLastLocation(gpsId)).thenThrow(new RuntimeException());

		gpsResource.getLocationById(gpsId);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenExceptionWhenStoringLocationThenRethrow() {

		ZonedDateTime time = ZonedDateTime.now();

		com.byznass.tiolktrack.jaxrs.resource.dto.Location location =
				new com.byznass.tiolktrack.jaxrs.resource.dto.Location("111", "222", time.toString());
		Location modelLocation = new Location("111", "222", time, "xxx");

		when(locationMapper.toModel(location, "xxx")).thenReturn(modelLocation);
		when(persistLocationHandler.persist(modelLocation)).thenThrow(TiolkTrackException.class);

		gpsResource.createLocationForGps("xxx", location);
	}

	@Test
	public void givenPersistingLocationThenReturnCorrectResponse() {

		ZonedDateTime time = ZonedDateTime.now();

		com.byznass.tiolktrack.jaxrs.resource.dto.Location location =
				new com.byznass.tiolktrack.jaxrs.resource.dto.Location("111", "222", time.toString());
		Location modelLocation = new Location("111", "222", time, "xxx");
		Location resultModel = new Location("111_1", "222_2", time.plusDays(1), "xxx");

		com.byznass.tiolktrack.jaxrs.resource.dto.Location expected =
				new com.byznass.tiolktrack.jaxrs.resource.dto.Location("111_1", "222_2", time.plusDays(1).toString());

		when(locationMapper.toModel(location, "xxx")).thenReturn(modelLocation);
		when(persistLocationHandler.persist(modelLocation)).thenReturn(resultModel);
		when(locationMapper.toDto(resultModel)).thenReturn(expected);

		com.byznass.tiolktrack.jaxrs.resource.dto.Location actual = gpsResource.createLocationForGps("xxx", location);

		assertEquals(expected, actual);
	}
}