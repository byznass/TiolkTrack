package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.GpsPersister;
import com.byznass.tiolktrack.kernel.model.gps.Gps;
import com.byznass.tiolktrack.postgres.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgresGpsPersister implements GpsPersister {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostgresGpsPersister.class);

	private final ConnectionProvider connectionProvider;

	@Inject
	public PostgresGpsPersister(ConnectionProvider connectionProvider) {

		this.connectionProvider = connectionProvider;
	}

	@Override
	public void persist(Gps gps) throws TiolkTrackException {

		LOGGER.info("Persisting a new GPS entity (\'{},{}\').", gps.getUserId(), gps.getName());

		String query = "INSERT INTO gps (clientId, name) VALUES(?, ?);";
		try (Connection connection = connectionProvider.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, gps.getUserId());
			preparedStatement.setString(2, gps.getName());

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows != 1) {
				LOGGER.error("Error while persisting a new GPS entity (\'{},{}\'), affected row not equal to 1 but {}", gps.getUserId(), gps.getName(), affectedRows);
				throw new TiolkTrackException(String.format("Persisting error on INSERT into database of a new GPS entity (\'%s,%s\').", gps.getUserId(), gps.getName()));
			}

			LOGGER.info("Successfully persisted a new GPS entity (\'{},{}\') into database.", gps.getUserId(), gps.getName());

		} catch (SQLException e) {
			LOGGER.error("Error while persisting a new GPS entity (\'{},{}\') into database.", gps.getUserId(), gps.getName(), e);
			throw new TiolkTrackException(String.format("Cannot persist a new GPS entity (\'%s,%s\').", gps.getUserId(), gps.getName()), e);
		}
	}
}
