package com.byznass.tiolktrack.postgres;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Connection;

public class LiquibaseUpdateRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(LiquibaseUpdateRunner.class);
	private static final String CHANGE_LOG_FILE = "db/databaseChangeLog.xml";

	private final ConnectionProvider connectionProvider;

	@Inject
	public LiquibaseUpdateRunner(ConnectionProvider connectionProvider) {

		this.connectionProvider = connectionProvider;
	}

	public void update() {

		try {
			LOGGER.info("Starting Liquibase update");
			Connection connection = connectionProvider.getConnection();
			JdbcConnection jdbcConnection = new JdbcConnection(connection);
			Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

			Liquibase liquibase = new Liquibase(CHANGE_LOG_FILE, new ClassLoaderResourceAccessor(), database);
			liquibase.update(new Contexts(), new LabelExpression());
			LOGGER.info("Finished Liquibase update");
		} catch (LiquibaseException | ConnectionFailureException e) {
			LOGGER.error("Liquibase update failed", e);
			throw new LiquibaseUpdateException("Liquibase update failed", e);
		}
	}
}
