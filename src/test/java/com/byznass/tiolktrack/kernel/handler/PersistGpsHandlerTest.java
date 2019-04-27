package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.GpsPersister;
import com.byznass.tiolktrack.kernel.dao.NoUserWithSuchIdException;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.model.gps.Gps;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersistGpsHandlerTest {

	@Mock
	GpsPersister gpsPersister;
	@Mock
	UserProvider userProvider;

	private PersistGpsHandler persistGpsHander;

	@Before
	public void setUp() {

		initMocks(this);
		persistGpsHander = new PersistGpsHandler(userProvider, gpsPersister);
	}

	@Test(expected = InvalidIdentifierException.class)
	public void givenInvalidGpsNameThenThrowException() {

		Gps gps = new Gps("user", "12345@!@#$das");

		persistGpsHander.persistGps(gps);
	}

	@Test(expected = InvalidIdentifierException.class)
	public void givenTooLongGpsNameThenThrowException() {

		Gps gps = new Gps("user", "1adsadkjasbkdjbaskdbkasbkdbuasbdbasdg83eg238egybdas");

		persistGpsHander.persistGps(gps);
	}

	@Test(expected = NoUserWithSuchIdException.class)
	public void givenNonExistentUserThenThrowException() {

		Gps gps = new Gps("user", "1adsadkjasbkdjbaskdbkasbkdbuasbdbasdg83eg238egybda");

		when(userProvider.exists(gps.getUserId())).thenReturn(false);

		persistGpsHander.persistGps(gps);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenExceptionWhilePersistingThenThrowException() {

		Gps gps = new Gps("user", "1adsadkjasbkdjbaskdbkasbkdbuasbdbasdg83eg238egybda");

		when(userProvider.exists(gps.getUserId())).thenReturn(true);
		doThrow(TiolkTrackException.class).when(gpsPersister).persist(gps);

		persistGpsHander.persistGps(gps);
	}

	@Test
	public void givenNewGpsWhenPersistingThenSucceed() {

		Gps gps = new Gps("user", "1adsadkjasbkdjbaskdbkasbkdbuasbdbasdg83eg238egybda");

		when(userProvider.exists(gps.getUserId())).thenReturn(true);

		Gps acctualGps = persistGpsHander.persistGps(gps);

		assertEquals(gps, acctualGps);
	}
}