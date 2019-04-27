package com.byznass.tiolktrack.kernel.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.model.gps.Gps;

public interface GpsPersister {

	void persist(Gps gps) throws TiolkTrackException;
}
