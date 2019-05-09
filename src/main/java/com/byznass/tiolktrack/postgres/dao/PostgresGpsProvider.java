package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.LocationProvider;
import com.byznass.tiolktrack.kernel.dao.NoSuchGpsException;
import com.byznass.tiolktrack.kernel.model.Location;
import com.byznass.tiolktrack.kernel.model.gps.Gps;
import com.byznass.tiolktrack.kernel.model.gps.GpsWithLocations;
import com.byznass.tiolktrack.postgres.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostgresGpsProvider implements GpsProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostgresGpsProvider.class);

	private final ConnectionProvider connectionProvider;
	private final LocationProvider locationProvider;

	@Inject
	public PostgresGpsProvider(ConnectionProvider connectionProvider, LocationProvider locationProvider) {

		this.connectionProvider = connectionProvider;
		this.locationProvider = locationProvider;
	}

	@Override
	public GpsWithLocations getGpsWithLocations(String userID, String name) throws NoSuchGpsException, TiolkTrackException {

		LOGGER.info("Retrieving GpsWithLocations entity (\'{},{}\') from postgres database", userID, name);
		Gps gps = getGps(userID, name);
		List<Location> locations = locationProvider.getLocationsForGps(userID, name);
		LOGGER.info("Successfully retrieved GpsWithLocations entity (\'{},{}\') from postgres database", userID, name);

		return new GpsWithLocations(gps, locations);
	}

	@Override
	public Gps getGps(String userID, String name) throws NoSuchGpsException, TiolkTrackException {

		LOGGER.info("Retrieving simple GPS entity (\'{},{}\') from postgres database", userID, name);

		String query = "SELECT * FROM gps WHERE clientId=? AND name=?";
		try (Connection connection = connectionProvider.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, userID);
			preparedStatement.setString(2, name);

			return extractGps(userID, name, preparedStatement);
		} catch (SQLException e) {
			LOGGER.error("Error while retrieving GPS entity (\'{},{}\') from database", userID, name, e);
			throw new TiolkTrackException(String.format("Error while retrieving GPS entity (\'%s, %s\') from database", userID, name), e);
		}
	}

	private Gps extractGps(String userID, String name, PreparedStatement preparedStatement) throws SQLException {

		try (ResultSet resultSet = preparedStatement.executeQuery()) {

			if (!resultSet.next()) {
				LOGGER.error("No GPS entity (\'{},{}\') in postgres database", userID, name);
				throw new NoSuchGpsException(String.format("No GPS entity (\'%s, %s\') exists", userID, name));
			}

			return new Gps(userID, name);
		}
	}

}
