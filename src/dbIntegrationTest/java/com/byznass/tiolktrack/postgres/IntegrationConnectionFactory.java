package com.byznass.tiolktrack.postgres;

import com.byznass.tiolktrack.config.NoSuchPropertyException;
import com.byznass.tiolktrack.config.PropertyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.byznass.tiolktrack.config.Property.DB_INTEGRATION_TEST_HOST;

public class IntegrationConnectionFactory implements ConnectionProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationConnectionFactory.class);

	private static final String URL = "jdbc:postgresql://%s/tiolktrack";
	private static final String DEFAULT_DB_HOST = "localhost";
	private static final String DB_USERNAME = "tiolktrack";
	private static final String DB_PASSWORD = "dbIntegrationTest";

	private static final int RETRY_LIMIT = 1024;

	private String dbHost;

	public IntegrationConnectionFactory(PropertyProvider propertyProvider) {

		try {
			dbHost = propertyProvider.getValue(DB_INTEGRATION_TEST_HOST);
		} catch (NoSuchPropertyException e) {
			LOGGER.error("No DB_HOST specified. Using default: {}", DEFAULT_DB_HOST);
			dbHost = DEFAULT_DB_HOST;
		}
	}

	public Connection getConnection() throws ConnectionFailureException {

		int currentRetry = 1;
		while (true) {
			try {
				Thread.sleep(2000);

				return tryToGetConnection();
			} catch (ClassNotFoundException | SQLException | NoSuchPropertyException | InterruptedException e) {
				LOGGER.error("Cannot connect to database on attempt {}", currentRetry);
				currentRetry++;
				if (currentRetry == RETRY_LIMIT) {
					throw new ConnectionFailureException("Cannot obtain database connection", e);
				}
			}
		}
	}

	private Connection tryToGetConnection() throws SQLException, ClassNotFoundException {

		LOGGER.info("Trying to create a postgres connection");
		Class.forName("org.postgresql.Driver");
		Properties connectionDetails = getConnectionDetails();

		return DriverManager.getConnection(String.format(URL, dbHost), connectionDetails);
	}

	private Properties getConnectionDetails() {

		Properties props = new Properties();
		props.setProperty("user", DB_USERNAME);
		props.setProperty("password", DB_PASSWORD);
		props.setProperty("ssl", "true");
		props.setProperty("sslmode", "allow");

		return props;
	}
}