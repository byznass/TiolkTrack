package com.byznass.tiolktrack;

import com.byznass.tiolktrack.binder.TiolkTrackBinder;
import com.byznass.tiolktrack.resource.jaxrs.GpsResource;
import com.byznass.tiolktrack.resource.jaxrs.exception.mapper.NoGpsWIthIdExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class TiolkTrackApplication extends ResourceConfig {

	public TiolkTrackApplication() {

		registerInstances(new TiolkTrackBinder());

		register(GpsResource.class);

		register(NoGpsWIthIdExceptionMapper.class);
	}

}
