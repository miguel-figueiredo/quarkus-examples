package test.quarkus;

import com.bugsnag.Bugsnag;
import com.td.athena.errorhandling.ServiceException;
import io.smallrye.mutiny.Uni;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonException;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.quarkus.errors.ServiceErrorCode;

@Path("/hello")
@ApplicationScoped
public class ExampleResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleResource.class);

    @ConfigProperty(name = "application.name")
    private String name;

    @Inject
    private Bugsnag bugsnag;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ExampleResponse> hello() {
        //bugsnag.notify(new IllegalArgumentException("Direct notification"));

        LOGGER.error("Quarkus bugsnag error", new UnsupportedOperationException());
//        LOGGER.warn("Warn error", new NoSuchElementException());
//        LOGGER.info("Information error", new UnknownError());
//        LOGGER.debug("Debug error", new NullPointerException());
//        LOGGER.trace("Trace error", new RuntimeException());


        return Uni.createFrom().item(new ExampleResponse(name));
        //return Uni.createFrom().failure(new ServiceException(ServiceErrorCode.BAD_REQUEST));
    }

    @AllArgsConstructor
    @Getter
    public static class ExampleResponse {
        private String text;
    }
}