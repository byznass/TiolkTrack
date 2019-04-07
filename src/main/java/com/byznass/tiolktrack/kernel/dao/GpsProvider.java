package com.byznass.tiolktrack.kernel.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.model.Gps;

public interface GpsProvider {

	Gps getGpsById(String gpsId) throws NoGpsWithIdException, TiolkTrackException;
}
