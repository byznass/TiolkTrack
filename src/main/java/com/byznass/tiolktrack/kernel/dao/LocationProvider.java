package com.byznass.tiolktrack.kernel.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.model.Location;

import java.util.List;

public interface LocationProvider {

	List<Location> getLocationsForGps(String userId, String gpsName) throws TiolkTrackException;
}
