package com.byznass.tiolktrack.plsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	private static final String URL = "jdbc:postgresql://postgres-server/tiolktrack";

	public Connection createConnection() {

		try {
			Class.forName("org.postgresql.Driver");

			Properties props = new Properties();
			props.setProperty("user", "tiolktrack");
			props.setProperty("password", "tiolktrack2");
			props.setProperty("ssl", "true");
			props.setProperty("sslmode", "allow");

			return DriverManager.getConnection(URL, props);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
