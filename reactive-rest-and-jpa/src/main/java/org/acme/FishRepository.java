package org.acme;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FishRepository implements PanacheRepository<Fish> {

}
