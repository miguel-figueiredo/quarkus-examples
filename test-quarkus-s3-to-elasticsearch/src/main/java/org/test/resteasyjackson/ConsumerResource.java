package org.test.resteasyjackson;

import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Path("/consume")
@Slf4j
public class ConsumerResource {

    private UserRepository repository;
    private UserReader reader;

    @Inject
    public ConsumerResource(final UserRepository repository, final UserReader reader) {
        this.repository = repository;
        this.reader = reader;
    }

    @GET
    public Response consume() throws IOException {
        log.info("Consuming parquet files");

        repository.persist(User.builder()
            .id("1")
            .name("Miguel")
            .company("Nokia")
            .build());

        reader.stream().forEach(repository::updateCompany);

        return Response.ok().build();
    }
}
