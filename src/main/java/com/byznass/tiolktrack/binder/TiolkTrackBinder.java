package com.byznass.tiolktrack.binder;

import com.byznass.tiolktrack.config.PropertyProvider;
import com.byznass.tiolktrack.jaxrs.resource.GpsResource;
import com.byznass.tiolktrack.jaxrs.resource.GpsResourceImpl;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.LocationMapper;
import com.byznass.tiolktrack.jaxrs.resource.exception.mapper.NoGpsWIthIdExceptionMapper;
import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.dao.LocationProvider;
import com.byznass.tiolktrack.kernel.handler.GpsLocationById;
import com.byznass.tiolktrack.postgres.ConnectionProvider;
import com.byznass.tiolktrack.postgres.dao.PostgresGpsProvider;
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

		bind(GpsLocationById.class).to(GpsLocationById.class);
		bind(PostgresGpsProvider.class).to(GpsProvider.class);
		bind(PostgresLocationProvider.class).to(LocationProvider.class);
	}

	private void bindJaxRsResources() {

		bind(GpsResourceImpl.class).to(GpsResource.class);

		bind(LocationMapper.class).to(LocationMapper.class);
		bind(NoGpsWIthIdExceptionMapper.class).to(NoGpsWIthIdExceptionMapper.class);
	}

	private void bindEnvironmentResources() {

		bind(PropertyProvider.class).to(PropertyProvider.class);
		bind(com.byznass.tiolktrack.postgres.ConnectionFactory.class).to(ConnectionProvider.class);

		bindFactory(ConnectionFactory.class).to(Connection.class);
	}
}
