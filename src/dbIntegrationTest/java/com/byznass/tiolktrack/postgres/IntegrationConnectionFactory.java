package com.byznass.tiolktrack.postgres;

import com.byznass.tiolktrack.config.NoSuchPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class IntegrationConnectionFactory implements ConnectionProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationConnectionFactory.class);

	private static final String URL = "jdbc:postgresql://localhost/tiolktrack";
	private static final String DB_USERNAME = "tiolktrack";
	private static final String DB_PASSWORD = "dbIntegrationTest";

	private static final int RETRY_LIMIT = 5;

	public Connection getConnection() throws ConnectionFailureException {

		int currentRetry = 0;
		while (true) {
			try {
				Thread.sleep(currentRetry * 2000);

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

	private Connection tryToGetConnection() throws SQLException, InterruptedException, ClassNotFoundException {

		LOGGER.info("Trying to create a postgres connection");
		Class.forName("org.postgresql.Driver");
		Properties connectionDetails = getConnectionDetails();

		return DriverManager.getConnection(URL, connectionDetails);
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