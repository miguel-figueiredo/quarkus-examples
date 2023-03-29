package org.acme;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/fishes")
public class FishResource {

    private FishRepository repository;

    public FishResource(FishRepository repository) {
        this.repository = repository;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ReactiveTransactional
    public Uni<Fish> createFish(Fish fish) {
        return repository.persist(fish);
    }
}