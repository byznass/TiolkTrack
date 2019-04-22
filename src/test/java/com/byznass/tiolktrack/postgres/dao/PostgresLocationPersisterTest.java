package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.LocationPersister;
import com.byznass.tiolktrack.kernel.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PostgresLocationPersisterTest {

	@Mock
	private Connection connection;

	private LocationPersister locationPersister;

	@Before
	public void setUp() {

		initMocks(this);
		locationPersister = new PostgresLocationPersister(connection);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenSQLExceptionWhilePersistingLocationThenThrowException() throws Exception {

		LocalDateTime time = LocalDateTime.now();
		Location location = new Location("lat1", "long2", time, "gpsid1");
		PreparedStatement preparedStatement = mock(PreparedStatement.class);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		doThrow(SQLException.class).when(preparedStatement).setTimestamp(anyInt(), eq(Timestamp.valueOf(time)));

		locationPersister.persistLocation(location);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenNoAffectedRowsWhilePersistingLocationThenThrowException() throws Exception {

		LocalDateTime time = LocalDateTime.now();
		Location location = new Location("lat1", "long2", time, "gpsid1");
		PreparedStatement preparedStatement = mock(PreparedStatement.class);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeUpdate()).thenReturn(0);

		locationPersister.persistLocation(location);
	}

	@Test
	public void givenOneAffectedRowWhilePersistingLocationThenSuccess() throws Exception {

		LocalDateTime time = LocalDateTime.now();
		Location location = new Location("lat1", "long2", time, "gpsid1");
		PreparedStatement preparedStatement = mock(PreparedStatement.class);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeUpdate()).thenReturn(1);

		locationPersister.persistLocation(location);
	}
}