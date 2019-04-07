package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.LocationProvider;
import com.byznass.tiolktrack.kernel.dao.NoGpsWithIdException;
import com.byznass.tiolktrack.kernel.model.Gps;
import com.byznass.tiolktrack.kernel.model.Location;
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

	private final Connection connection;
	private final LocationProvider locationProvider;

	@Inject
	public PostgresGpsProvider(Connection connection, LocationProvider locationProvider) {

		this.connection = connection;
		this.locationProvider = locationProvider;
	}

	@Override
	public Gps getGpsById(String gpsId) throws NoGpsWithIdException, TiolkTrackException {

		try {
			LOGGER.info("Retrieving Gps entity with id=\"{}\" from postgres database", gpsId);
			checkIfExists(gpsId);
			List<Location> locations = locationProvider.getLocationsForGps(gpsId);

			return new Gps(gpsId, locations);
		} catch (SQLException e) {
			LOGGER.error("Error while retrieving Gps entity with id=\"{}\" from postgres", gpsId, e);
			throw new TiolkTrackException(String.format("Cannot retrieve Gps with id=\"%s\" from database", gpsId), e);
		}
	}

	private void checkIfExists(String gpsId) throws SQLException {

		if (!exists(gpsId)) {
			LOGGER.error("No Gps entity with id=\"{}\" in postgres database", gpsId);
			throw new NoGpsWithIdException(String.format("No GPS with id=\"%s\" exists", gpsId));
		}
	}

	private boolean exists(String gpsId) throws SQLException {

		String query = "SELECT * FROM gps WHERE id=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, gpsId);
			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		}
	}
}
