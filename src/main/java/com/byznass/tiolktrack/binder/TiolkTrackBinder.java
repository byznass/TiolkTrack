package com.byznass.tiolktrack.binder;

import com.byznass.tiolktrack.config.PropertyProvider;
import com.byznass.tiolktrack.config.SystemTimeProvider;
import com.byznass.tiolktrack.config.TimeProvider;
import com.byznass.tiolktrack.jaxrs.resource.GpsResource;
import com.byznass.tiolktrack.jaxrs.resource.GpsResourceImpl;
import com.byznass.tiolktrack.jaxrs.resource.dto.LocationValidator;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.LocationMapper;
import com.byznass.tiolktrack.jaxrs.resource.exception.mapper.NoGpsWIthIdExceptionMapper;
import com.byznass.tiolktrack.jaxrs.resource.exception.mapper.NoLocationForGpsExceptionMapper;
import com.byznass.tiolktrack.jaxrs.resource.exception.mapper.RuntimeExceptionMapper;
import com.byznass.tiolktrack.jaxrs.resource.exception.mapper.TiolkTrackExceptionMapper;
import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.LocationPersister;
import com.byznass.tiolktrack.kernel.dao.LocationProvider;
import com.byznass.tiolktrack.kernel.handler.GetGpsLocationHandler;
import com.byznass.tiolktrack.kernel.handler.PersistLocationHandler;
import com.byznass.tiolktrack.postgres.ConnectionProvider;
import com.byznass.tiolktrack.postgres.dao.PostgresGpsProvider;
import com.byznass.tiolktrack.postgres.dao.PostgresLocationPersister;
import com.byznass.tiolktrack.postgres.dao.PostgresLocationProvider;
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

		bind(PostgresGpsProvider.class).to(GpsProvider.class);
		bind(PostgresLocationProvider.class).to(LocationProvider.class);
		bind(PostgresLocationPersister.class).to(LocationPersister.class);
	}

	private void bindJaxRsResources() {

		bind(GpsResourceImpl.class).to(GpsResource.class);

		bind(LocationMapper.class).to(LocationMapper.class);
		bind(NoGpsWIthIdExceptionMapper.class).to(NoGpsWIthIdExceptionMapper.class);
		bind(NoLocationForGpsExceptionMapper.class).to(NoLocationForGpsExceptionMapper.class);
		bind(RuntimeExceptionMapper.class).to(RuntimeExceptionMapper.class);
		bind(TiolkTrackExceptionMapper.class).to(TiolkTrackExceptionMapper.class);
		bind(LocationValidator.class).to(LocationValidator.class);
	}

	private void bindEnvironmentResources() {

		bind(PropertyProvider.class).to(PropertyProvider.class);
		bind(com.byznass.tiolktrack.postgres.ConnectionFactory.class).to(ConnectionProvider.class);
		bind(SystemTimeProvider.class).to(TimeProvider.class);

		bindFactory(ConnectionFactory.class).to(Connection.class);
	}
}
