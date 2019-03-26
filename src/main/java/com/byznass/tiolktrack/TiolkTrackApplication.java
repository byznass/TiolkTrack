package com.byznass.tiolktrack;

import com.byznass.tiolktrack.binder.TiolkTrackBinder;
import com.byznass.tiolktrack.postgres.LiquibaseUpdateRunner;
import com.byznass.tiolktrack.resource.jaxrs.GpsResource;
import com.byznass.tiolktrack.resource.jaxrs.exception.mapper.NoGpsWIthIdExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class TiolkTrackApplication extends ResourceConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(TiolkTrackApplication.class);

	public TiolkTrackApplication() {

		LOGGER.info("Starting application initialization");

		initializeApplication();

		registerInstances(new TiolkTrackBinder());

		register(GpsResource.class);

		register(NoGpsWIthIdExceptionMapper.class);

		LOGGER.info("Finished application initialization");
	}

	private void initializeApplication() {

		new LiquibaseUpdateRunner().update();
	}

}
