package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.model.Gps;
import com.byznass.tiolktrack.kernel.model.Location;
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

	public Location getLastLocation(String gpsId) {

		LOGGER.info("Trying to get current location of GPS with id=\'{}\'", gpsId);
		Gps gps = gpsProvider.getGpsById(gpsId);

		Optional<Location> location = gps.getLastLocation();
		if (!location.isPresent()) {
			LOGGER.error("GPS with id=\'{}\' has no location", gpsId);
			throw new NoLocationForGpsException(gpsId);
		}

		LOGGER.info("Successfully retrieved last location for GPS with id=\'{}\'", gpsId);

		return location.get();
	}
}
