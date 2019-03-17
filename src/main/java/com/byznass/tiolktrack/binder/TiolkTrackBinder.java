package com.byznass.tiolktrack.binder;

import com.byznass.tiolktrack.resource.Gps;
import com.byznass.tiolktrack.resource.handler.GetGpsById;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class TiolkTrackBinder extends AbstractBinder {
    @Override
    protected void configure() {

        bind(GetGpsById.class).to(GetGpsById.class);

        bind(Gps.class).to(Gps.class);
    }
}
