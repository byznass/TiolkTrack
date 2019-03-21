package com.byznass.tiolktrack.resource;

import com.byznass.tiolktrack.resource.dto.Location;
import com.byznass.tiolktrack.resource.handler.GetGpsById;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/gps")
@Singleton
public class Gps {

    private final GetGpsById getGpsById;

    @Inject
    public Gps(GetGpsById getGpsById) {

        this.getGpsById = getGpsById;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{gpsId}/location")
    public Location getLocationById(@PathParam("gpsId") String gpsId) {

        return getGpsById.execute(gpsId);
    }
}