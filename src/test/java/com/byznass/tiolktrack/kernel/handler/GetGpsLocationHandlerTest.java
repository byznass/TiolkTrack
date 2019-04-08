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
		String gpsId = "id";
		Gps gps = mock(Gps.class);

		ZonedDateTime time = ZonedDateTime.of(2019, 3, 24, 23, 8, 10, 0, ZoneId.of("UTC"));
		Location expectedLocation = new Location("456", "123", time, gpsId);

		when(gpsProvider.getGpsById(gpsId)).thenReturn(gps);
		when(gps.getLastLocation()).thenReturn(Optional.of(expectedLocation));

		// execute
		Location actualLocation = handler.getLastLocation(gpsId);

		// verify
		verify(gpsProvider).getGpsById(gpsId);
		assertEquals(expectedLocation, actualLocation);
	}

	@Test(expected = NoLocationForGpsException.class)
	public void givenCorrectGpsIdWhenGettingLocationForGpsWithoutLocationsThenThrowException() {

		// setup
		String gpsId = "id";
		Gps gps = mock(Gps.class);

		when(gpsProvider.getGpsById(gpsId)).thenReturn(gps);
		when(gps.getLastLocation()).thenReturn(Optional.empty());

		// execute
		handler.getLastLocation(gpsId);
	}

	@Test(expected = NoGpsWithIdException.class)
	public void givenNonexistentGpsIdWhenGettingLocationThenThrowException() {

		// setup
		when(gpsProvider.getGpsById("123")).thenThrow(new NoGpsWithIdException("No GPS with given id."));

		// execute
		handler.getLastLocation("123");
	}
}