package com.byznass.tiolktrack;

import com.byznass.tiolktrack.postgres.IntegrationConnectionFactory;
import com.byznass.tiolktrack.postgres.LiquibaseUpdateRunner;
import org.junit.Before;

public class DatabaseIntegrationTest {

	protected IntegrationConnectionFactory integrationConnectionFactory;

	@Before
	public void initializeDatabase() {

		integrationConnectionFactory = new IntegrationConnectionFactory();

		clearDatabase();
	}

	private void clearDatabase() {

		LiquibaseUpdateRunner liquibaseUpdateRunner = new LiquibaseUpdateRunner(integrationConnectionFactory);

		liquibaseUpdateRunner.dropAll();
		liquibaseUpdateRunner.update();
	}
}
