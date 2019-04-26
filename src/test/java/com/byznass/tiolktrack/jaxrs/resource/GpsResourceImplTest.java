package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.GpsDto;
import com.byznass.tiolktrack.jaxrs.resource.dto.InvalidDtoException;
import com.byznass.tiolktrack.jaxrs.resource.dto.LocationValidator;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.GpsMapper;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.LocationMapper;
import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.handler.GetGpsLocationHandler;
import com.byznass.tiolktrack.kernel.handler.PersistGpsHandler;
import com.byznass.tiolktrack.kernel.handler.PersistLocationHandler;
import com.byznass.tiolktrack.kernel.model.Location;
import com.byznass.tiolktrack.kernel.model.gps.Gps;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GpsResourceImplTest {

	@Mock
	private GetGpsLocationHandler getGpsLocationHandler;
	@Mock
	private LocationMapper locationMapper;
	@Mock
	private PersistLocationHandler persistLocationHandler;
	@Mock
	private LocationValidator locationValidator;
	@Mock
	private GpsMapper gpsMapper;
	@Mock
	private PersistGpsHandler persistGpsHandler;

	private GpsResourceImpl gpsResource;

	@Before
	public void setUp() {

		initMocks(this);

		gpsResource =
				new GpsResourceImpl(getGpsLocationHandler, persistLocationHandler, locationMapper, locationValidator, gpsMapper, persistGpsHandler);
	}

	@Test
	public void whenGettingCurrentLocationOfGpsThenReturnCorrectResponse() {

		// setup
		String userId = "xyz";
		String gpsName = "123";
		LocalDateTime time = LocalDateTime.of(2019, 3, 24, 22, 56, 23, 11);
		Location modelLocation = new Location("47.151154", "27.589897", time, userId, gpsName);
		com.byznass.tiolktrack.jaxrs.resource.dto.Location expectedLocation =
				new com.byznass.tiolktrack.jaxrs.resource.dto.Location("47.151154", "27.589897", time.toString());

		when(getGpsLocationHandler.getLastLocation(userId, gpsName)).thenReturn(modelLocation);
		when(locationMapper.toDto(modelLocation)).thenReturn(expectedLocation);

		// execute
		com.byznass.tiolktrack.jaxrs.resource.dto.Location actualLocation = gpsResource.getLocation(userId, gpsName);

		// verify
		assertEquals(expectedLocation, actualLocation);
		verify(getGpsLocationHandler).getLastLocation(userId, gpsName);
		verify(locationMapper).toDto(modelLocation);
	}

	@Test(expected = RuntimeException.class)
	public void givenAnExceptionIsThrownWhenGettingCurrentLocationThenRethrow() {

		String userId = "xyz";
		String gpsName = "123";

		when(getGpsLocationHandler.getLastLocation(userId, gpsName)).thenThrow(new RuntimeException());

		gpsResource.getLocation(userId, gpsName);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenExceptionWhenStoringLocationThenRethrow() {

		LocalDateTime time = LocalDateTime.now();

		com.byznass.tiolktrack.jaxrs.resource.dto.Location location =
				new com.byznass.tiolktrack.jaxrs.resource.dto.Location("111", "222", time.toString());
		Location modelLocation = new Location("111", "222", time, "xxx", "123");

		when(locationMapper.toModel(location, "xxx", "123")).thenReturn(modelLocation);
		when(persistLocationHandler.persist(modelLocation)).thenThrow(TiolkTrackException.class);

		gpsResource.createLocationForGps("xxx", "123", location);
	}

	@Test
	public void givenPersistingLocationThenReturnCorrectResponse() {

		LocalDateTime time = LocalDateTime.now();

		com.byznass.tiolktrack.jaxrs.resource.dto.Location location =
				new com.byznass.tiolktrack.jaxrs.resource.dto.Location("111", "222", time.toString());
		Location modelLocation = new Location("111", "222", time, "xxx", "123");
		Location resultModel = new Location("111_1", "222_2", time.plusDays(1), "xxx", "123");

		com.byznass.tiolktrack.jaxrs.resource.dto.Location expected =
				new com.byznass.tiolktrack.jaxrs.resource.dto.Location("111_1", "222_2", time.plusDays(1).toString());

		when(locationMapper.toModel(location, "xxx", "123")).thenReturn(modelLocation);
		when(persistLocationHandler.persist(modelLocation)).thenReturn(resultModel);
		when(locationMapper.toDto(resultModel)).thenReturn(expected);

		com.byznass.tiolktrack.jaxrs.resource.dto.Location actual =
				gpsResource.createLocationForGps("xxx", "123", location);

		assertEquals(expected, actual);
	}

	@Test(expected = InvalidDtoException.class)
	public void givenInvalidDtoThenRethrowException() {

		com.byznass.tiolktrack.jaxrs.resource.dto.Location location =
				mock(com.byznass.tiolktrack.jaxrs.resource.dto.Location.class);

		doThrow(InvalidDtoException.class).when(locationValidator).validate(location);

		gpsResource.createLocationForGps("xxx", "123", location);
	}

	@Test
	public void givenANewGpsWhenPersistingThenSucceed() {

		String userId = "user";
		GpsDto gpsDto = new GpsDto("name");

		Gps gps = new Gps(userId, "name");
		when(gpsMapper.toModel(gpsDto, userId)).thenReturn(gps);

		Gps gps1 = new Gps(userId, "name");
		when(persistGpsHandler.persistGps(gps)).thenReturn(gps1);

		when(gpsMapper.toDto(gps1)).thenReturn(new GpsDto("name"));

		GpsDto actualGps = gpsResource.createGps(userId, gpsDto);
		GpsDto expectedGps = new GpsDto("name");

		assertEquals(expectedGps, actualGps);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenExceptionWhilePersistingNewGpsThenRethrow() {

		String userId = "user";
		GpsDto gpsDto = new GpsDto("name");

		Gps gps = new Gps(userId, "name");
		when(gpsMapper.toModel(gpsDto, userId)).thenReturn(gps);

		when(persistGpsHandler.persistGps(gps)).thenThrow(TiolkTrackException.class);

		gpsResource.createGps(userId, gpsDto);
	}

}