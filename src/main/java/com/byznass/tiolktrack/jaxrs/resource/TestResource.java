package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.postgres.ConnectionProvider;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/test")
public class TestResource {

	private final ConnectionProvider connectionProvider;

	@Inject
	public TestResource(ConnectionProvider connectionProvider) {

		this.connectionProvider = connectionProvider;
	}

	@GET
	public String getAllLine() {

		String query = "SELECT * FROM testTable";
		try (ResultSet resultSet = connectionProvider.getConnection().createStatement().executeQuery(query)) {

			StringBuilder result = new StringBuilder();
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				result.append(id);
				result.append("\n");
			}

			return result.toString();
		} catch (SQLException e) {
			throw new TestResourceException("ERROR");
		}
	}
}
