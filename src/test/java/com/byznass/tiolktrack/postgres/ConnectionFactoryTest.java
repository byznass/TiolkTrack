package com.byznass.tiolktrack.postgres;

import com.byznass.tiolktrack.config.NoSuchPropertyException;
import com.byznass.tiolktrack.config.PropertyProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.byznass.tiolktrack.config.Property.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionFactory.class)
public class ConnectionFactoryTest {

	@Mock
	private PropertyProvider propertyProvider;

	private ConnectionProvider connectionProvider;

	@Before
	public void setUp() {

		initMocks(this);

		Mockito.when(propertyProvider.getValue(DB_HOST)).thenReturn("host");
		Mockito.when(propertyProvider.getValue(DB_USERNAME)).thenReturn("username");
		Mockito.when(propertyProvider.getValue(DB_PASSWORD)).thenReturn("password");

		mockStatic(DriverManager.class);

		connectionProvider = new ConnectionFactory(propertyProvider);
	}

	@Test(expected = ConnectionFailureException.class)
	public void givenConnectionErrorWhenTruingToCreateDBConnectionThenThrowException() throws Exception {

		when(DriverManager.getConnection(anyString(), any(Properties.class))).thenThrow(SQLException.class);

		connectionProvider.getConnection();
	}

	@Test
	public void givenCorrectPropertyValuesWhenCreateDBConnectionThenReturnConnection() throws Exception {

		Connection expectedConnection = mock(Connection.class);

		Properties props = new Properties();
		props.setProperty("user", "username");
		props.setProperty("password", "password");
		props.setProperty("ssl", "true");
		props.setProperty("sslmode", "allow");

		when(DriverManager.getConnection(eq(String.format(ConnectionFactory.URL, "host")), eq(props))).thenReturn(expectedConnection);

		Connection actualConnection = connectionProvider.getConnection();

		assertEquals(expectedConnection, actualConnection);
	}

	@Test(expected = ConnectionFailureException.class)
	public void givenNonexistentPropertyWHenCreateDBConnectionThenThrowException() {

		Mockito.when(propertyProvider.getValue(DB_USERNAME)).thenThrow(NoSuchPropertyException.class);

		connectionProvider.getConnection();
	}
}