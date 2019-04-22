package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.Location;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/gps")
public interface GpsResource {

	@GET
	@Produces(APPLICATION_JSON)
	@Path("{gpsId}/location")
	Location getLocationById(@PathParam("gpsId") String gpsId);

	@POST
	@Produces(APPLICATION_JSON)
	@Path("{gpsId}/location")
	Location createLocationForGps(@PathParam("gpsId") String gpsId, Location location);
}
