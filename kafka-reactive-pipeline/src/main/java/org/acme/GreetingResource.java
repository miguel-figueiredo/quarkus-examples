package org.acme;

import io.smallrye.mutiny.Uni;
import java.security.SecureRandom;
import java.time.Duration;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    private SecureRandom random = new SecureRandom();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> hello(@QueryParam("name") String name) {
        return Uni.createFrom().item("Hello " + name)
//            .onItem().delayIt().by(Duration.ofMillis(random.nextInt(200, 4000)));
            .onItem().delayIt().by(Duration.ofMillis(1000));
    }
}