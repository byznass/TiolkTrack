package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.NoUserWithSuchIdException;
import com.byznass.tiolktrack.postgres.ConnectionProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

public class PostgresUserProviderTest {

	@Mock
	private ConnectionProvider connectionProvider;

	private PostgresUserProvider userProvider;

	@Before
	public void setUp() {

		initMocks(this);

		userProvider = new PostgresUserProvider(connectionProvider);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenSQLExceptionWhileGettingUserThenThrowException() throws Exception {

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		Connection connection = mock(Connection.class);

		when(connectionProvider.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenThrow(SQLException.class);

		userProvider.getUser("userId");
	}

	@Test(expected = NoUserWithSuchIdException.class)
	public void givenInvalidUserIdWhenRetrievingUserThenThrowException() throws Exception {

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		ResultSet resultSet = createResultSet();
		Connection connection = mock(Connection.class);

		when(connectionProvider.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);

		userProvider.getUser("invalidUserId");
	}

	@Test
	public void givenNonExistentUserWhenCheckingIfExistsThenReturnFalse() throws Exception {

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		ResultSet resultSet = createResultSet();
		Connection connection = mock(Connection.class);

		when(connectionProvider.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);

		assertFalse(userProvider.exists("userId"));
	}

	@Test
	public void givenExistentUserWhenCheckingIfExistsThenReturnTrue() throws Exception {

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		ResultSet resultSet = createResultSet();
		Connection connection = mock(Connection.class);

		when(connectionProvider.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		assertTrue(userProvider.exists("userId"));
	}

	@Test(expected = TiolkTrackException.class)
	public void givenExceptionWhenCheckingIfUserIdExistsThenReturnThrowException() throws Exception {

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		Connection connection = mock(Connection.class);

		when(connectionProvider.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenThrow(SQLException.class);

		userProvider.exists("userId");
	}

	private ResultSet createResultSet() throws SQLException {

		ResultSet resultSet = mock(ResultSet.class);

		when(resultSet.next()).thenReturn(true);
		when(resultSet.getString("id")).thenReturn("userId");
		when(resultSet.getBytes("passHash")).thenReturn(new byte[]{1, 2, 3});
		when(resultSet.getBytes("passSalt")).thenReturn(new byte[]{4, 5, 6});

		return resultSet;
	}
}