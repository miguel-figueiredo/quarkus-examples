package org.acme;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.time.Duration;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
public class KafkaDelayedPipeline {

    @RestClient
    private GreetingService greetingService;

    /**
     * Message ack strategy is pre-processing.
     * If an error occurs after the message is received, the message is not consumed again.
     **/
    @Incoming("words-delayed-in")
    @Outgoing("words-delayed-out")
    @NonBlocking
    public Multi<String> execute(Multi<String> messages) {
        return messages
            .onItem().invoke(s -> log.info("Received: {}", s))
            .onItem().call(() -> Uni.createFrom().voidItem().onItem().delayIt().by(Duration.ofSeconds(5)))
            .onItem().invoke(s -> log.info("Sending: {}", s));
    }
}
