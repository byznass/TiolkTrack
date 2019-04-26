package com.byznass.tiolktrack.kernel.dao;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.kernel.model.Location;

public interface LocationPersister {

	void persistLocation(Location location) throws TiolkTrackException;
}
