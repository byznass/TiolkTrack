package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.NoSuchGpsException;
import com.byznass.tiolktrack.kernel.model.Location;
import com.byznass.tiolktrack.kernel.model.gps.GpsWithLocations;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetGpsLocationHandlerTest {

	@Mock
	private GpsProvider gpsProvider;

	private GetGpsLocationHandler handler;

	@Before
	public void setUp() {

		initMocks(this);

		handler = new GetGpsLocationHandler(gpsProvider);
	}

	@Test
	public void givenCorrectGpsIdWhenGettingLocationForGpsWithLocationsThenReturnLastOne() {

		// setup
		String userId = "id";
		String gpsName = "name";
		GpsWithLocations gpsWithLocations = mock(GpsWithLocations.class);

		LocalDateTime time = LocalDateTime.of(2019, 3, 24, 23, 8, 10, 0);
		Location expectedLocation = new Location("456", "123", time, userId, gpsName);

		when(gpsProvider.getGpsWithLocations(userId, gpsName)).thenReturn(gpsWithLocations);
		when(gpsWithLocations.getLastLocation()).thenReturn(Optional.of(expectedLocation));

		// execute
		Location actualLocation = handler.getLastLocation(userId, gpsName);

		// verify
		verify(gpsProvider).getGpsWithLocations(userId, gpsName);
		assertEquals(expectedLocation, actualLocation);
	}

	@Test(expected = NoLocationForGpsException.class)
	public void givenCorrectGpsIdWhenGettingLocationForGpsWithoutLocationsThenThrowException() {

		// setup
		String userId = "id";
		String gpsName = "name";
		GpsWithLocations gpsWithLocations = mock(GpsWithLocations.class);

		when(gpsProvider.getGpsWithLocations(userId, gpsName)).thenReturn(gpsWithLocations);
		when(gpsWithLocations.getLastLocation()).thenReturn(Optional.empty());

		// execute
		handler.getLastLocation(userId, gpsName);
	}

	@Test(expected = NoSuchGpsException.class)
	public void givenNonexistentGpsIdWhenGettingLocationThenThrowException() {

		// setup
		when(gpsProvider.getGpsWithLocations("loh", "123")).thenThrow(new NoSuchGpsException("No GPS with given id."));

		// execute
		handler.getLastLocation("loh", "123");
	}
}