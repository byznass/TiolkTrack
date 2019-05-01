package com.byznass.tiolktrack;

import com.byznass.tiolktrack.config.PropertyProvider;
import com.byznass.tiolktrack.postgres.IntegrationConnectionFactory;
import com.byznass.tiolktrack.postgres.LiquibaseUpdateRunner;
import org.junit.Before;

public abstract class DatabaseIntegrationTest {

	protected IntegrationConnectionFactory integrationConnectionFactory;

	@Before
	public void initializeDatabase() {

		PropertyProvider propertyProvider = new PropertyProvider();
		integrationConnectionFactory = new IntegrationConnectionFactory(propertyProvider);

		clearDatabase();
	}

	private void clearDatabase() {

		LiquibaseUpdateRunner liquibaseUpdateRunner = new LiquibaseUpdateRunner(integrationConnectionFactory);

		liquibaseUpdateRunner.dropAll();
		liquibaseUpdateRunner.update();
	}
}
