package org.acme;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/fishes")
public class FishResource {

    private FishRepository repository;
    private FishApiService service;

    @Inject
    public FishResource(FishRepository repository, @RestClient FishApiService service) {
        this.repository = repository;
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Fish createFish(Fish fish) {
        repository.persist(fish);
        return fish;
    }

    @GET
    @Path("/{id}")
    public String getFish(@PathParam("id") String id) {
        return service.get(id);
    }
}