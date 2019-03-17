package com.byznass.tiolktrack;

import com.byznass.tiolktrack.binder.TiolkTrackBinder;
import com.byznass.tiolktrack.resource.Gps;
import org.glassfish.jersey.server.ResourceConfig;

public class TiolkTrackApplication extends ResourceConfig {

    public TiolkTrackApplication() {

        registerInstances(new TiolkTrackBinder());

        register(Gps.class);
    }

}
