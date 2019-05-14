package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.UserPersister;
import com.byznass.tiolktrack.kernel.model.User;
import com.byznass.tiolktrack.postgres.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgresUserPersister implements UserPersister {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostgresUserPersister.class);

	private final ConnectionProvider connectionProvider;

	@Inject
	public PostgresUserPersister(ConnectionProvider connectionProvider) {

		this.connectionProvider = connectionProvider;
	}

	@Override
	public void persist(User user) {

		LOGGER.info("Persisting a new user");

		String query = "INSERT INTO client (id, passHash, passSalt) VALUES(?, ?, ?);";
		try (Connection connection = connectionProvider.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, user.getId());
			preparedStatement.setBytes(2, user.getPassHash());
			preparedStatement.setBytes(3, user.getPassSalt());

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows != 1) {
				LOGGER.error("Error while persisting a new user, affected row not equal to 1 but {}", affectedRows);
				throw new TiolkTrackException("Persisting error on INSERT into database of a new user");
			}

			LOGGER.info("Successfully persisted a new user into database.");

		} catch (SQLException e) {
			LOGGER.error("Error while persisting a new user into database", e);
			throw new TiolkTrackException("Cannot persist a new user into database", e);
		}
	}
}
