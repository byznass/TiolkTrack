package com.byznass.tiolktrack.binder;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import com.byznass.tiolktrack.postgres.ConnectionProvider;
import org.glassfish.hk2.api.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory implements Factory<Connection> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);

	private final ConnectionProvider connectionProvider;

	@Inject
	public ConnectionFactory(ConnectionProvider connectionProvider) {

		this.connectionProvider = connectionProvider;
	}

	@Override
	public Connection provide() {

		return connectionProvider.getConnection();
	}

	@Override
	public void dispose(Connection instance) {

		try {
			instance.close();
		} catch (SQLException e) {
			LOGGER.error("Error while closing database connection.", e);
			throw new TiolkTrackException("Error while closing database connection.", e);
		}
	}
}
