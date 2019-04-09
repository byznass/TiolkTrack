package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.LocationProvider;
import com.byznass.tiolktrack.kernel.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PostgresLocationProviderTest {

	@Mock
	private Connection connection;

	private LocationProvider locationProvider;

	@Before
	public void setUp() {

		initMocks(this);
		locationProvider = new PostgresLocationProvider(connection);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenSQLExceptionWhileQueryingDatabaseThenThrowException() throws Exception {

		PreparedStatement preparedStatement = mock(PreparedStatement.class);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenThrow(SQLException.class);

		locationProvider.getLocationsForGps("134");
	}

	@Test
	public void givenNoLinesFromDatabaseThenReturnEmptyListOfLocations() throws Exception {

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		ResultSet resultSet = mock(ResultSet.class);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);


		List<Location> locations = locationProvider.getLocationsForGps("134");

		assertEquals(0, locations.size());
	}

	@Test
	public void givenSomeLinesFromDatabaseThenReturnCorrectListOfLocation() throws Exception {

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		ResultSet resultSet = getResultSet();

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		List<Location> locations = locationProvider.getLocationsForGps("134");

		assertEquals(2, locations.size());

		Location location = locations.get(0);
		assertEquals("134", location.getGpsId());
		assertEquals("lat1", location.getLatitude());
		assertEquals("long1", location.getLongitude());
		assertEquals(LocalDateTime.of(2019, 4, 5, 6, 7, 3, 2), location.getTime());

		location = locations.get(1);
		assertEquals("134", location.getGpsId());
		assertEquals("lat2", location.getLatitude());
		assertEquals("long2", location.getLongitude());
		assertEquals(LocalDateTime.of(2020, 4, 5, 6, 7, 3, 2), location.getTime());

	}

	private ResultSet getResultSet() throws Exception {

		ResultSet resultSet = mock(ResultSet.class);

		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		when(resultSet.getString("latitude")).thenReturn("lat1").thenReturn("lat2");
		when(resultSet.getString("longitude")).thenReturn("long1").thenReturn("long2");
		when(resultSet.getString("gpsId")).thenReturn("134").thenReturn("134");

		Timestamp timestamp1 = Timestamp.valueOf(LocalDateTime.of(2019, 4, 5, 6, 7, 3, 2));
		Timestamp timestamp2 = Timestamp.valueOf(LocalDateTime.of(2020, 4, 5, 6, 7, 3, 2));

		when(resultSet.getTimestamp("time")).thenReturn(timestamp1).thenReturn(timestamp2);

		return resultSet;
	}

}