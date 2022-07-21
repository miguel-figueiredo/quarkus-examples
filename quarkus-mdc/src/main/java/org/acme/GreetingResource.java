package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Path("/hello")
@Slf4j
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @MdcLogged
    public String hello() {
        log.info("Received request");
        return "Hello from RESTEasy Reactive";
    }

    // Without the @MdcLogged annotation therefore will not be filtered.
    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@PathParam("name") String name) {
        return "Hello " + name;
    }
}