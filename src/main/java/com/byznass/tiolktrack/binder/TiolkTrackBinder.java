package com.byznass.tiolktrack.binder;

import com.byznass.tiolktrack.config.PropertyProvider;
import com.byznass.tiolktrack.jaxrs.resource.GpsResource;
import com.byznass.tiolktrack.jaxrs.resource.GpsResourceImpl;
import com.byznass.tiolktrack.jaxrs.resource.TestResource;
import com.byznass.tiolktrack.jaxrs.resource.dto.mapper.LocationMapper;
import com.byznass.tiolktrack.jaxrs.resource.exception.mapper.NoGpsWIthIdExceptionMapper;
import com.byznass.tiolktrack.kernel.dao.GpsProvider;
import com.byznass.tiolktrack.kernel.handler.GpsLocationById;
import com.byznass.tiolktrack.postgres.ConnectionFactory;
import com.byznass.tiolktrack.postgres.ConnectionProvider;
import com.byznass.tiolktrack.postgres.dao.PlsqlGpsProvider;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.inject.Singleton;

public class TiolkTrackBinder extends AbstractBinder {

	@Override
	protected void configure() {

		bind(GpsLocationById.class).to(GpsLocationById.class).in(Singleton.class);
		bind(PlsqlGpsProvider.class).to(GpsProvider.class);

		bind(GpsResourceImpl.class).to(GpsResource.class).in(Singleton.class);
		bind(TestResource.class).to(TestResource.class).in(RequestScoped.class);
		bind(LocationMapper.class).to(LocationMapper.class).in(Singleton.class);
		bind(NoGpsWIthIdExceptionMapper.class).to(NoGpsWIthIdExceptionMapper.class);

		bind(PropertyProvider.class).to(PropertyProvider.class);
		bind(ConnectionFactory.class).to(ConnectionProvider.class);
	}
}
