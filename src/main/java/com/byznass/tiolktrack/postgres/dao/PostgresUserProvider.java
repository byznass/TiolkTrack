package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.NoUserWithSuchIdException;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresUserProvider implements UserProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostgresUserProvider.class);

	private final Connection connection;

	@Inject
	public PostgresUserProvider(Connection connection) {

		this.connection = connection;
	}


	@Override
	public User getUser(String userId) throws NoUserWithSuchIdException {

		LOGGER.info("Retrieving User with userId=\"{}\" from postgres database", userId);

		String query = "SELECT * FROM client WHERE id=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				LOGGER.error("No User with userId=\"{}\" exist in postgres database", userId);
				throw new NoUserWithSuchIdException(userId);
			}

			String id = resultSet.getString("id");
			byte[] passHash = resultSet.getBytes("passHash");
			byte[] passSalt = resultSet.getBytes("passSalt");

			return new User(id, passHash, passSalt);
		} catch (SQLException e) {
			LOGGER.error("Error while retrieving User with userId=\'{}\' from postgres database", userId, e);
			throw new TiolkTrackException(String.format("Cannot retrieve User with userId =  \'%s\' from database", userId), e);
		}

	}
}
