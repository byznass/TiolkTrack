package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

public class PostgresUserPersisterTest {

	@Mock
	private Connection connection;

	private PostgresUserPersister userPersister;

	@Before
	public void setUp() {

		initMocks(this);

		userPersister = new PostgresUserPersister(connection);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenSQLExceptionWhilePersistingNewUserThenThrowException() throws Exception {

		User user = new User("userId", new byte[0], new byte[0]);
		PreparedStatement preparedStatement = mock(PreparedStatement.class);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);

		userPersister.persist(user);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenNoAffectedRowsWhilePersistingUserThenThrowException() throws Exception {

		User user = new User("userId", new byte[0], new byte[0]);
		PreparedStatement preparedStatement = mock(PreparedStatement.class);

		Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		Mockito.when(preparedStatement.executeUpdate()).thenReturn(0);

		userPersister.persist(user);
	}

	@Test
	public void givenOneAffectedRowWhilePersistingUserThenSuccess() throws Exception {

		User user = new User("userId", new byte[0], new byte[0]);
		PreparedStatement preparedStatement = mock(PreparedStatement.class);

		Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);

		userPersister.persist(user);
	}
}