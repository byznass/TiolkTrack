package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.resource.dto.Location;

import javax.inject.Singleton;

@Singleton
public class GetGpsById {

    public Location execute(String gpsId) {

        // TODO (TT-20) implement this class
        return new Location("id", gpsId);
    }
}
