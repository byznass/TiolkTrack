package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
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
	private final CoordinateValidator coordinateValidator;

	@Inject
	public PersistLocationHandler(LocationPersister locationPersister, GpsProvider gpsProvider,
								  CoordinateValidator coordinateValidator) {

		this.locationPersister = locationPersister;
		this.gpsProvider = gpsProvider;
		this.coordinateValidator = coordinateValidator;
	}

	public Location persist(Location location) {

		coordinateValidator.validate(location);

		LOGGER.info("Persisting location for GPS entity (\'{},{}\')", location.getUserId(), location.getGpsName());
		checkIfGpsExists(location.getUserId(), location.getGpsName());
		locationPersister.persistLocation(location);
		LOGGER.info("Successfully persisted location for GPS entity (\'{},{}\')", location.getUserId(), location.getGpsName());

		return location;
	}

	private void checkIfGpsExists(String userId, String gpsName) {

		try {
			gpsProvider.getGps(userId, gpsName);
		} catch (TiolkTrackException e) {
			LOGGER.error("Failed to check that location is for a valid GPS");
			throw new TiolkTrackException("Failed to check that location is for a valid GPS", e);
		}
	}
}
