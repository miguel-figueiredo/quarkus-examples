package test.quarkus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
public class ExampleResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public void hello() {
        LOGGER.error("XPTO", new RuntimeException());
    }

    public class ExampleException extends Exception {

    }
}