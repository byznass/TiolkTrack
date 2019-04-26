package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.Location;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/{userId}/gps")
public interface GpsResource {

	@GET
	@Produces(APPLICATION_JSON)
	@Path("{gpsName}/location")
	Location getLocation(@PathParam("userId") String userId, @PathParam("gpsName") String gpsName);

	@POST
	@Produces(APPLICATION_JSON)
	@Path("{gpsName}/location")
	Location createLocationForGps(@PathParam("userId") String userId, @PathParam("gpsName") String gpsName, Location locationDto);
}
