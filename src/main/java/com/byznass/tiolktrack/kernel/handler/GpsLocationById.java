package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.model.Gps;
import com.byznass.tiolktrack.kernel.model.Location;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GpsLocationById {

	private final GpsProvider gpsProvider;

	@Inject
	GpsLocationById(GpsProvider gpsProvider) {

		this.gpsProvider = gpsProvider;
	}

	public Location execute(String gpsId) {

		Gps gps = gpsProvider.getGpsById(gpsId);

		return gps.getCurrentLocation();
	}
}
