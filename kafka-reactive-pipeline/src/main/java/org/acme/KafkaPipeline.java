package org.acme;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.*;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
public class KafkaPipeline {

    @RestClient
    private GreetingService greetingService;

    /**
     * Consume the message from the "words-in" channel, uppercase it and send it to the uppercase channel.
     * Messages come from the broker.
     **/
    @Incoming("words-in")
    @Outgoing("words-out")
    @NonBlocking
    public Multi<String> execute(Multi<String> messages) {
        return messages
            .onItem().invoke(s -> log.info("Received: {}", s))
            //.onItem().call(() -> Uni.createFrom().voidItem().onItem().delayIt().by(Duration.ofSeconds(10)))
            .onItem().transformToUniAndMerge(greetingService::hello)
            .onItem().invoke(s -> log.info("Sending: {}", s));
    }
}
