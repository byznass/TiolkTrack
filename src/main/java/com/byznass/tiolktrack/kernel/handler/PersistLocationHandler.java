package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.LocationPersister;
import com.byznass.tiolktrack.kernel.dao.NoGpsWithIdException;
import com.byznass.tiolktrack.kernel.model.Location;

public class PersistLocationHandler {

	private final LocationPersister locationPersister;
	private final GpsProvider gpsProvider;

	public PersistLocationHandler(LocationPersister locationPersister, GpsProvider gpsProvider) {

		this.locationPersister = locationPersister;
		this.gpsProvider = gpsProvider;
	}

	public Location persist(Location location) {

		//TODO add validation of latitude and longitude

		checkIfGpsExists(location.getGpsId());
		locationPersister.persistLocation(location);

		return location;
	}

	private void checkIfGpsExists(String gpsId) {

		if (!gpsProvider.exists(gpsId)) {
			throw new NoGpsWithIdException(String.format("No GPS with id=\"%s\"", gpsId));
		}
	}
}
