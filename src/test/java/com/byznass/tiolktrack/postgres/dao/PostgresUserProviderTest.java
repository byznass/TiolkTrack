package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.NoUserWithSuchIdException;
import com.byznass.tiolktrack.kernel.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

public class PostgresUserProviderTest {

	@Mock
	private Connection connection;

	private PostgresUserProvider userProvider;

	@Before
	public void setUp() {

		initMocks(this);

		userProvider = new PostgresUserProvider(connection);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenSQLExceptionWhileQueryingDatabaseThenThrowException() throws Exception {

		PreparedStatement preparedStatement = mock(PreparedStatement.class);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenThrow(SQLException.class);

		userProvider.getUser("userId");
	}

	@Test(expected = NoUserWithSuchIdException.class)
	public void givenInvalidUserIdWhenRetrievingUserThenThrowException() throws Exception {

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		ResultSet resultSet = createResultSet();

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);

		userProvider.getUser("invalidUserId");
	}

	@Test
	public void givenCorrectUserIdThenReturnRetrievedUser() throws Exception {

		PreparedStatement preparedStatement = mock(PreparedStatement.class);
		ResultSet resultSet = createResultSet();

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		User user = userProvider.getUser("userId");

		assertEquals("userId", user.getId());
		assertArrayEquals(new byte[]{1, 2, 3}, user.getPassHash());
		assertArrayEquals(new byte[]{4, 5, 6}, user.getPassSalt());
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