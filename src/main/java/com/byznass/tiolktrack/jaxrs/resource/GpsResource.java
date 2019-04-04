package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.Location;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/gps")
@Singleton
public interface GpsResource {

	@GET
	@Produces(APPLICATION_JSON)
	@Path("{gpsId}/location")
	Location getLocationById(@PathParam("gpsId") String gpsId);
}
