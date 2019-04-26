package com.byznass.tiolktrack.kernel.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.model.gps.Gps;
import com.byznass.tiolktrack.kernel.model.gps.GpsWithLocations;

public interface GpsProvider {

	GpsWithLocations getGpsWithLocations(String userId, String name) throws NoSuchGpsException, TiolkTrackException;
	Gps getGps(String userId, String name) throws NoSuchGpsException, TiolkTrackException;
}
