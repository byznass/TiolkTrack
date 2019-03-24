package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.NoGpsWithIdException;
import com.byznass.tiolktrack.kernel.model.Gps;
import com.byznass.tiolktrack.kernel.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GpsLocationByIdTest {

	@Mock
	private GpsProvider gpsProvider;

	private GpsLocationById handler;

	@Before
	public void setUp() {

		initMocks(this);

		handler = new GpsLocationById(gpsProvider);
	}

	@Test
	public void givenCorrectGpsIdWhenGettingLocationThenReturnCorrectly() {

		// setup
		String gpsId = "id";
		Gps gps = mock(Gps.class);
		Location expectedLocation =
				new Location("456", "123", ZonedDateTime.of(2019, 3, 24, 23, 8, 10, 0, ZoneId.of("+02:00")));

		when(gpsProvider.getGpsById(gpsId)).thenReturn(gps);
		when(gps.getCurrentLocation()).thenReturn(expectedLocation);

		// execute
		Location actualLocation = handler.execute(gpsId);

		// verify
		verify(gpsProvider).getGpsById(gpsId);
		assertEquals(expectedLocation, actualLocation);
	}

	@Test(expected = NoGpsWithIdException.class)
	public void givenNonexistentGpsIdWhenGettingLocationThenThrowException() {

		// setup
		when(gpsProvider.getGpsById("123")).thenThrow(new NoGpsWithIdException("No GPS with given id."));

		// execute
		handler.execute("123");
	}
}