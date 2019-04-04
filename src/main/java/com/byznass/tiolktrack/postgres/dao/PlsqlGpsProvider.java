package com.byznass.tiolktrack.postgres.dao;

import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.NoGpsWithIdException;
import com.byznass.tiolktrack.kernel.model.Gps;
import com.byznass.tiolktrack.kernel.model.Location;

import java.time.ZonedDateTime;
import java.util.Objects;

public class PlsqlGpsProvider implements GpsProvider {

	@Override
	public Gps getGpsById(String gpsId) throws NoGpsWithIdException {

		//TODO (TT-20) implement this provider

		if ("123".equals(gpsId)) {
			throw new NoGpsWithIdException("No GPS with given id.");
		}

		Location location =
				new Location(String.valueOf(Objects.hash(gpsId, "gunoi")), String.valueOf(Objects.hash(gpsId)), ZonedDateTime.now());

		Gps gps = new Gps();
		gps.setCurrentLocation(location);

		return gps;
	}
}
