package com.byznass.tiolktrack.binder;

import com.byznass.tiolktrack.resource.Gps;
import com.byznass.tiolktrack.resource.handler.GetGpsById;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

public class TiolkTrackBinder extends AbstractBinder {

    @Override
    protected void configure() {

        bind(GetGpsById.class).to(GetGpsById.class).in(Singleton.class);

        bind(Gps.class).to(Gps.class).in(Singleton.class);
    }
}
