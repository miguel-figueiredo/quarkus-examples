package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RegisterRestClient
@Path("/species")
public interface FishApiService {

    @GET
    @Path("/{id}")
    String get(@PathParam("id") String id);
}
