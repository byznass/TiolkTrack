package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.LocationPersister;
import com.byznass.tiolktrack.kernel.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class PersistLocationHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistLocationHandler.class);

	private final LocationPersister locationPersister;
	private final GpsProvider gpsProvider;

	@Inject
	public PersistLocationHandler(LocationPersister locationPersister, GpsProvider gpsProvider) {

		this.locationPersister = locationPersister;
		this.gpsProvider = gpsProvider;
	}

	public Location persist(Location location) {

		//TODO(TT-31) add validation of latitude and longitude
		LOGGER.info("Persisting location for GPS entity (\'{},{}\')", location.getUserId(), location.getGpsName());
		checkIfGpsExists(location.getUserId(), location.getGpsName());
		locationPersister.persistLocation(location);
		LOGGER.info("Successfully persisted location for GPS entity (\'{},{}\')", location.getUserId(), location.getGpsName());

		return location;
	}

	private void checkIfGpsExists(String userId, String gpsName) {

		gpsProvider.getGps(userId, gpsName);
	}
}
