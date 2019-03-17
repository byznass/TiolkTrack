package com.byznass.tiolktrack.resource;

import com.byznass.tiolktrack.resource.dto.Location;
import com.byznass.tiolktrack.resource.handler.GetGpsById;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/gps")
public class Gps {

    private final GetGpsById getGpsById;

    @Inject
    public Gps(GetGpsById getGpsById) {

        this.getGpsById = getGpsById;
    }

    @GET
    @Path("{gpsId}/location")
    public Location getLocationById(@PathParam("gpsId") String gpsId) {

        return getGpsById.execute(gpsId);
    }
}

