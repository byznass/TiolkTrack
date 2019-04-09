package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.LocationPersister;
import com.byznass.tiolktrack.kernel.dao.NoGpsWithIdException;
import com.byznass.tiolktrack.kernel.model.Location;
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

	private PersistLocationHandler persistLocationHandler;

	@Before
	public void setUp() {

		initMocks(this);

		persistLocationHandler = new PersistLocationHandler(locationPersister, gpsProvider);
	}

	@Test(expected = NoGpsWithIdException.class)
	public void givenNonExistentGpsWithGivenIdWhenPersistingThenThrowException() {

		Location location = new Location("xxx", "yyy", LocalDateTime.now(), "zzz");

		when(gpsProvider.exists("zzz")).thenReturn(false);

		persistLocationHandler.persist(location);
	}

	@Test
	public void givenExistingGpsWhilePersistingLocationThenSuccess() {

		Location location = new Location("xxx", "yyy", LocalDateTime.now(), "zzz");

		when(gpsProvider.exists("zzz")).thenReturn(true);

		Location actual = persistLocationHandler.persist(location);

		verify(locationPersister).persistLocation(location);
		assertEquals(location, actual);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenExceptionWhilePersistingLocationThenRethrow() {

		Location location = new Location("xxx", "yyy", LocalDateTime.now(), "zzz");

		when(gpsProvider.exists("zzz")).thenReturn(true);
		doThrow(TiolkTrackException.class).when(locationPersister).persistLocation(location);

		persistLocationHandler.persist(location);
	}
}