package com.byznass.tiolktrack.jaxrs.resource;

import com.byznass.tiolktrack.jaxrs.resource.dto.User;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/user")
public interface UserResource {

	@POST
	@Produces(APPLICATION_JSON)
	Response createUser(User user);

}
