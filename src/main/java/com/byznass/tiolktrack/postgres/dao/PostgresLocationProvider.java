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
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
	public List<Location> getLocationsForGps(String gpsId) {

		LOGGER.info("Retrieving Locations with gpsId=\"{}\" from postgres database", gpsId);

		String query = "SELECT * FROM location WHERE gpsId=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, gpsId);
			ResultSet resultSet = preparedStatement.executeQuery();

			return extractLocations(resultSet);
		} catch (SQLException e) {
			LOGGER.error("Error while retrieving Locations with gpsId=\"{}\" from postgres database", gpsId, e);
			throw new TiolkTrackException(String.format("Cannot retrieve Locations for gps with id =  \"%s\" from database", gpsId), e);
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
		ZonedDateTime time = resultSet.getTimestamp("time").toLocalDateTime().atZone(ZoneId.of("UTC"));
		String gpsIdOfCurrentLocation = resultSet.getString("gpsId");

		return new Location(latitude, longitude, time, gpsIdOfCurrentLocation);
	}
}
