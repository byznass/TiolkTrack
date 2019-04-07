package com.byznass.tiolktrack.binder;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.postgres.ConnectionProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConnectionFactoryTest {

	@Mock
	private ConnectionProvider connectionProvider;

	private ConnectionFactory connectionFactory;

	@Before
	public void setUp() {

		initMocks(this);

		connectionFactory = new ConnectionFactory(connectionProvider);
	}

	@Test(expected = TiolkTrackException.class)
	public void givenFailWhenClosingConnectionThenThrowException() throws Exception {

		Connection connection = mock(Connection.class);

		doThrow(SQLException.class).when(connection).close();

		connectionFactory.dispose(connection);
	}

	@Test
	public void givenRequestForConnectionCreationThenDelegateToProvider() {

		Connection expectedConnection = mock(Connection.class);

		when(connectionProvider.getConnection()).thenReturn(expectedConnection);

		Connection actualConnection = connectionFactory.provide();

		assertEquals(expectedConnection, actualConnection);
	}

	@Test
	public void givenDisposalRequestThenCloseConnection() throws Exception {

		Connection connection = mock(Connection.class);

		connectionFactory.dispose(connection);

		verify(connection).close();
	}
}