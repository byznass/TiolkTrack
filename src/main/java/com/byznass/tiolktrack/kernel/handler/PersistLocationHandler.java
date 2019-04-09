package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.LocationPersister;
import com.byznass.tiolktrack.kernel.dao.NoGpsWithIdException;
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

		//TODO add validation of latitude and longitude

		LOGGER.info("Persisting location for GPS with id=\'{}\'", location.getGpsId());

		checkIfGpsExists(location.getGpsId());
		locationPersister.persistLocation(location);

		LOGGER.info("Successfully persisted location for GPS with id=\'{}\'", location.getGpsId());

		return location;
	}

	private void checkIfGpsExists(String gpsId) {

		if (!gpsProvider.exists(gpsId)) {
			LOGGER.error("Failed to persist location because the GPS(id = \'{}\') it belongs to doesn't exist", gpsId);
			throw new NoGpsWithIdException(String.format("Failed to persist location because the GPS(id = \'%s\') it belongs to doesn't exist", gpsId));
		}
	}
}
