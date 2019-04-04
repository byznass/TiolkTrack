package com.byznass.tiolktrack.postgres;

import com.byznass.tiolktrack.config.NoSuchPropertyException;
import com.byznass.tiolktrack.config.PropertyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.byznass.tiolktrack.config.Property.*;

public class ConnectionFactory implements ConnectionProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);
	static final String URL = "jdbc:postgresql://%s/tiolktrack";

	private final PropertyProvider propertyProvider;

	@Inject
	public ConnectionFactory(PropertyProvider propertyProvider) {

		this.propertyProvider = propertyProvider;
	}

	@Override
	public Connection getConnection() throws ConnectionFailureException {

		try {
			LOGGER.info("Trying to create a postgres connection");
			Class.forName("org.postgresql.Driver");
			Properties connectionDetails = getConnectionDetails();

			String url = String.format(URL, propertyProvider.getValue(DB_HOST));

			return DriverManager.getConnection(url, connectionDetails);
		} catch (ClassNotFoundException | SQLException | NoSuchPropertyException e) {
			LOGGER.error("Cannot connect to database {}", String.format(URL, propertyProvider.getValue(DB_HOST)), e);
			throw new ConnectionFailureException("Cannot connect to database " + String.format(URL, propertyProvider.getValue(DB_HOST)), e);
		}
	}

	private Properties getConnectionDetails() {

		Properties props = new Properties();
		props.setProperty("user", propertyProvider.getValue(DB_USERNAME));
		props.setProperty("password", propertyProvider.getValue(DB_PASSWORD));
		props.setProperty("ssl", "true");
		props.setProperty("sslmode", "allow");

		return props;
	}
}
