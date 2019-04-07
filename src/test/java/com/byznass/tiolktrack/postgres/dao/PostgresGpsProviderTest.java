package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.LocationProvider;
import com.byznass.tiolktrack.kernel.dao.NoGpsWithIdException;
import com.byznass.tiolktrack.kernel.model.Gps;
import com.byznass.tiolktrack.kernel.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PostgresGpsProviderTest {

	@Mock
	private Connection connection;
	@Mock
	private LocationProvider locationProvider;

	private GpsProvider gpsProvider;

	@Before
	public void setUp() {

		initMocks(this);

		gpsProvider = new PostgresGpsProvider(connection, locationProvider);
	}

	@Test
	public void givenGpsIdWhenRetrievingGpsFromDbThenReturnCorrectGps() throws Exception {

		String gpsId = "123";

		Location[] locations = new Location[]{
				new Location("locid1", "xxx", "yyy", ZonedDateTime.now().minusDays(1), gpsId),
				new Location("locid2", "xxx", "yyy", ZonedDateTime.now().minusDays(2), gpsId)
		};

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		ResultSet resultSet = mock(ResultSet.class);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(locationProvider.getLocationsForGps(gpsId)).thenReturn(asList(locations));

		Gps actualGps = gpsProvider.getGpsById(gpsId);

		assertEquals(gpsId, actualGps.getId());
		assertThat(actualGps.getLocationsInTime(), containsInAnyOrder(locations));
	}

	@Test(expected = NoGpsWithIdException.class)
	public void givenInvalidGpsIdWhenRetrievingGpsThenThrowException() throws Exception {

		String gpsId = "123";

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		ResultSet resultSet = mock(ResultSet.class);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);

		gpsProvider.getGpsById(gpsId);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenGenericSqlErrorWhenRetrievingGpsThenThrowCorrectException() throws Exception {

		String gpsId = "123";

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		ResultSet resultSet = mock(ResultSet.class);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenThrow(SQLException.class);

		gpsProvider.getGpsById(gpsId);
	}
}