package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.model.Location;
import com.byznass.tiolktrack.kernel.model.gps.GpsWithLocations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;

public class GetGpsLocationHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetGpsLocationHandler.class);

	private final GpsProvider gpsProvider;

	@Inject
	GetGpsLocationHandler(GpsProvider gpsProvider) {

		this.gpsProvider = gpsProvider;
	}

	public Location getLastLocation(String userId, String gpsName) {

		LOGGER.info("Trying to get last location of GPS entity (\'{},{}\')", userId, gpsName);
		GpsWithLocations gpsWithLocations = gpsProvider.getGpsWithLocations(userId, gpsName);

		Optional<Location> location = gpsWithLocations.getLastLocation();
		if (!location.isPresent()) {
			LOGGER.error("GPS entity (\'{},{}\') has no location", userId, gpsName);
			throw new NoLocationForGpsException(userId, gpsName);
		}

		LOGGER.info("Successfully retrieved last location for GPS entity (\'{},{}\')", userId, gpsName);

		return location.get();
	}
}
