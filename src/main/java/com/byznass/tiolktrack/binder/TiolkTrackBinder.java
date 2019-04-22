package com.byznass.tiolktrack.binder;

import com.byznass.tiolktrack.config.PropertyProvider;
import com.byznass.tiolktrack.config.SystemTimeProvider;
import com.byznass.tiolktrack.config.TimeProvider;
import com.byznass.tiolktrack.jaxrs.exception.mapper.*;
import com.byznass.tiolktrack.jaxrs.filter.AuthenticationFilter;
import com.byznass.tiolktrack.jaxrs.resource.GpsResource;
import com.byznass.tiolktrack.jaxrs.resource.GpsResourceImpl;
import com.byznass.tiolktrack.jaxrs.resource.dto.LocationValidator;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.LocationMapper;
import com.byznass.tiolktrack.kernel.crypto.TokenEncrypter;
import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.LocationPersister;
import com.byznass.tiolktrack.kernel.dao.LocationProvider;
import com.byznass.tiolktrack.kernel.dao.UserProvider;
import com.byznass.tiolktrack.kernel.handler.AuthenticationHandler;
import com.byznass.tiolktrack.kernel.handler.GetGpsLocationHandler;
import com.byznass.tiolktrack.kernel.handler.PersistLocationHandler;
import com.byznass.tiolktrack.postgres.ConnectionProvider;
import com.byznass.tiolktrack.postgres.dao.PostgresGpsProvider;
import com.byznass.tiolktrack.postgres.dao.PostgresLocationPersister;
import com.byznass.tiolktrack.postgres.dao.PostgresLocationProvider;
import com.byznass.tiolktrack.postgres.dao.PostgresUserProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.sql.Connection;

public class TiolkTrackBinder extends AbstractBinder {

	@Override
	protected void configure() {

		bindKernel();
		bindJaxRsResources();
		bindEnvironmentResources();
	}

	private void bindKernel() {

		bind(GetGpsLocationHandler.class).to(GetGpsLocationHandler.class);
		bind(PersistLocationHandler.class).to(PersistLocationHandler.class);
		bind(AuthenticationHandler.class).to(AuthenticationHandler.class);

		bind(PostgresGpsProvider.class).to(GpsProvider.class);
		bind(PostgresLocationProvider.class).to(LocationProvider.class);
		bind(PostgresUserProvider.class).to(UserProvider.class);
		bind(PostgresLocationPersister.class).to(LocationPersister.class);

		bind(TokenEncrypter.class).to(TokenEncrypter.class);
	}

	private void bindJaxRsResources() {

		bind(GpsResourceImpl.class).to(GpsResource.class);

		bind(LocationMapper.class).to(LocationMapper.class);
		bind(LocationValidator.class).to(LocationValidator.class);

		bind(AuthenticationFilter.class).to(AuthenticationFilter.class);

		bind(AuthenticationExceptionMapper.class).to(AuthenticationExceptionMapper.class);
		bind(NoGpsWIthIdExceptionMapper.class).to(NoGpsWIthIdExceptionMapper.class);
		bind(NoLocationForGpsExceptionMapper.class).to(NoLocationForGpsExceptionMapper.class);
		bind(NoUserWithSuchIdExceptionMapper.class).to(NoUserWithSuchIdExceptionMapper.class);
		bind(RuntimeExceptionMapper.class).to(RuntimeExceptionMapper.class);
		bind(TiolkTrackExceptionMapper.class).to(TiolkTrackExceptionMapper.class);
		bind(InvalidDtoExceptionMapper.class).to(InvalidDtoExceptionMapper.class);
	}

	private void bindEnvironmentResources() {

		bind(PropertyProvider.class).to(PropertyProvider.class);
		bind(com.byznass.tiolktrack.postgres.ConnectionFactory.class).to(ConnectionProvider.class);
		bind(SystemTimeProvider.class).to(TimeProvider.class);

		bindFactory(ConnectionFactory.class).to(Connection.class);
	}
}
