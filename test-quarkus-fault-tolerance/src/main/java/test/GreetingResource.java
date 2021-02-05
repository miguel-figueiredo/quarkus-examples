package test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
public class GreetingResource {

    private static final Logger log = LoggerFactory.getLogger(GreetingResource.class);

    @GET
    @Retry(maxRetries = 5)
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        log.info("Requesting hello");
        if (Math.random() * 100 < 50) {
            log.error("Error");
            throw new RuntimeException();
        }
        return "Hello World!";
    }
}