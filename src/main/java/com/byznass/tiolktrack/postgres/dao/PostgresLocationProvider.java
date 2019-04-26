package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.dao.LocationProvider;
import com.byznass.tiolktrack.kernel.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostgresLocationProvider implements LocationProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostgresLocationProvider.class);

	private final Connection connection;

	@Inject
	public PostgresLocationProvider(Connection connection) {

		this.connection = connection;
	}

	@Override
	public List<Location> getLocationsForGps(String userId, String gpsName) {

		LOGGER.info("Retrieving Locations for gps \'{},{}\' from postgres database", userId, gpsName);

		String query = "SELECT * FROM location WHERE clientId=? AND gpsName=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, gpsName);
			ResultSet resultSet = preparedStatement.executeQuery();

			return extractLocations(resultSet);
		} catch (SQLException e) {
			LOGGER.error("Error while retrieving Locations with gps \'{},{}\' from postgres database", userId, gpsName, e);
			throw new TiolkTrackException(String.format("Cannot retrieve Locations for gps \'%s,%s\' from database", userId, gpsName), e);
		}
	}

	private List<Location> extractLocations(ResultSet resultSet) throws SQLException {

		List<Location> locations = new ArrayList<>();

		while (resultSet.next()) {
			Location location = getLocation(resultSet);
			locations.add(location);
		}

		return locations;
	}

	private Location getLocation(ResultSet resultSet) throws SQLException {

		String latitude = resultSet.getString("latitude");
		String longitude = resultSet.getString("longitude");
		LocalDateTime time = resultSet.getTimestamp("time").toLocalDateTime();
		String userId = resultSet.getString("clientId");
		String gpsName = resultSet.getString("gpsName");

		return new Location(latitude, longitude, time, userId, gpsName);
	}
}
