package com.byznass.tiolktrack;

import com.byznass.tiolktrack.binder.TiolkTrackBinder;
import com.byznass.tiolktrack.config.PropertyProvider;
import com.byznass.tiolktrack.jaxrs.exception.mapper.*;
import com.byznass.tiolktrack.jaxrs.filter.AuthenticationFilter;
import com.byznass.tiolktrack.jaxrs.resource.GpsResource;
import com.byznass.tiolktrack.jaxrs.resource.UserResource;
import com.byznass.tiolktrack.postgres.ConnectionFactory;
import com.byznass.tiolktrack.postgres.ConnectionProvider;
import com.byznass.tiolktrack.postgres.LiquibaseUpdateRunner;
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
		register(UserResource.class);

		register(AuthenticationExceptionMapper.class);
		register(InvalidDtoExceptionMapper.class);
		register(InvalidIdentifierExceptionMapper.class);
		register(NoSuchGpsExceptionMapper.class);
		register(NoLocationForGpsExceptionMapper.class);
		register(NoUserWithSuchIdExceptionMapper.class);
		register(RuntimeExceptionMapper.class);
		register(TiolkTrackExceptionMapper.class);
		register(UserAlreadyExistExceptionMapper.class);

		register(AuthenticationFilter.class);

		LOGGER.info("Finished application initialization");
	}

	private void initializeApplication() {

		PropertyProvider propertyProvider = new PropertyProvider();
		ConnectionProvider connectionProvider = new ConnectionFactory(propertyProvider);
		new LiquibaseUpdateRunner(connectionProvider).update();
	}

}
