package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.LocationPersister;
import com.byznass.tiolktrack.kernel.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PostgresLocationPersister implements LocationPersister {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostgresLocationPersister.class);

	private final Connection connection;

	@Inject
	public PostgresLocationPersister(Connection connection) {

		this.connection = connection;
	}

	@Override
	public void persistLocation(Location location) {

		LOGGER.info("Persisting location for GPS entity (\'{},{}\') into postgres database", location.getUserId(), location.getGpsName());

		String query = "INSERT INTO location (latitude, longitude, time, clientId, gpsName) VALUES(?, ?, ?, ?, ?);";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, location.getLatitude());
			preparedStatement.setString(2, location.getLongitude());
			preparedStatement.setTimestamp(3, Timestamp.valueOf(location.getTime()));
			preparedStatement.setString(4, location.getUserId());
			preparedStatement.setString(5, location.getGpsName());

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows != 1) {
				LOGGER.error("Error while persisting location, affected row not equal to 1 but {}", affectedRows);
				throw new TiolkTrackException(String.format("Persisting error on INSERT into database for GPS entity (\'%s, %s\')", location.getUserId(), location.getGpsName()));
			}

			LOGGER.info("Successfully persisted location into database.");

		} catch (SQLException e) {
			LOGGER.error("Error while persisting location for GPS entity (\'{},{}\') into postgres database", location.getUserId(), location.getGpsName(), e);
			throw new TiolkTrackException(String.format("Cannot persist location for GPS entity (\'%s, %s\') into database", location.getUserId(), location.getGpsName()), e);
		}
	}
}
