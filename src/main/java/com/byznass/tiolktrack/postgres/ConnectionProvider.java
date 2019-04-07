package com.byznass.tiolktrack.postgres;

import java.sql.Connection;

public interface ConnectionProvider {

	Connection getConnection() throws ConnectionFailureException;
}
