package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.LocationPersister;
import com.byznass.tiolktrack.kernel.dao.NoSuchGpsException;
import com.byznass.tiolktrack.kernel.model.Location;
import com.byznass.tiolktrack.kernel.model.gps.Gps;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersistLocationHandlerTest {

	@Mock
	private LocationPersister locationPersister;
	@Mock
	private GpsProvider gpsProvider;
	@Mock
	private CoordinateValidator coordinateValidator;

	private PersistLocationHandler persistLocationHandler;

	@Before
	public void setUp() {

		initMocks(this);

		persistLocationHandler = new PersistLocationHandler(locationPersister, gpsProvider, coordinateValidator);
	}

	@Test(expected = NoSuchGpsException.class)
	public void givenNonExistentGpsWithGivenIdWhenPersistingThenThrowException() {

		Location location = new Location("12.7759", "47.8856", LocalDateTime.now(), "userId", "name");

		when(gpsProvider.getGps("userId", "name")).thenThrow(NoSuchGpsException.class);

		persistLocationHandler.persist(location);
	}

	@Test
	public void givenExistingGpsWhilePersistingLocationThenSuccess() {

		Location location = new Location("12.7759", "47.8856", LocalDateTime.now(), "userId", "name");

		when(gpsProvider.getGps("userId", "name")).thenReturn(new Gps("userId", "name"));

		Location actual = persistLocationHandler.persist(location);

		verify(locationPersister).persistLocation(location);
		assertEquals(location, actual);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenExceptionWhilePersistingLocationThenRethrow() {

		Location location = new Location("12.7759", "47.8856", LocalDateTime.now(), "userId", "name");

		when(gpsProvider.getGps("userId", "name")).thenReturn(new Gps("userId", "name"));
		doThrow(TiolkTrackException.class).when(locationPersister).persistLocation(location);

		persistLocationHandler.persist(location);
	}

	@Test(expected = InvalidLocationException.class)
	public void givenFailedValidationOfCoordinatesThenRethrowException() {

		Location location = new Location("xxx", "yyy", LocalDateTime.now(), "userId", "name");

		doThrow(InvalidLocationException.class).when(coordinateValidator).validate(location);

		persistLocationHandler.persist(location);
	}
}