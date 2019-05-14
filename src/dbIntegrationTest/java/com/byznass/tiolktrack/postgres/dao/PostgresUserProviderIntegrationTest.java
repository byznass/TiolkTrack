package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.DatabaseIntegrationTest;
import com.byznass.tiolktrack.kernel.model.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class PostgresUserProviderIntegrationTest extends DatabaseIntegrationTest {

	private PostgresUserProvider postgresUserProvider;

	@Before
	public void setUp() {

		postgresUserProvider = new PostgresUserProvider(integrationConnectionFactory);
	}

	@Test
	public void testUserExtraction() throws SQLException {

		User expectedUser = new User("igori", new byte[]{1, 2, 3}, new byte[]{4, 5, 6});
		populateDatabase(expectedUser);

		User actualUser = postgresUserProvider.getUser("igori");

		assertEquals(expectedUser, actualUser);
	}

	private void populateDatabase(User user) throws SQLException {

		Connection connection = integrationConnectionFactory.getConnection();

		String query =
				"INSERT INTO public.client (id, passhash, passsalt) VALUES (?, ?, ?);";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, user.getId());
			preparedStatement.setBytes(2, user.getPassHash());
			preparedStatement.setBytes(3, user.getPassSalt());

			int numberOfAffectedRows = preparedStatement.executeUpdate();

			if (numberOfAffectedRows != 1) {
				throw new RuntimeException("Cannot prepare database for testing");
			}
		}
	}
}
