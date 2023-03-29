package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.concurrent.CompletionStage;

@RegisterRestClient
@Path("/species")
public interface FishApiService {

    @GET
    @Path("/{id}")
    CompletionStage<String> get(@PathParam("id") String id);
}
