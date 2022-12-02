package org.acme;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
public class KafkaManualAckWithEmitterPipeline {

    @RestClient
    private GreetingService greetingService;

    @Inject
    @Channel("words-manual-ack-emitter-out")
    private Emitter<String> emitter;

    /**
     * Doesn't commit anything.
     **/
    @Incoming("words-manual-ack-emitter-in")
    @NonBlocking
    public Uni<Void> execute(Message<String> message) {
        return Uni.createFrom().item(message.getPayload())
            .onItem().invoke(s -> log.info("Received: {}", s))
            .onItem().delayIt().by(Duration.ofSeconds(5))
            .onItem().transformToMulti(s -> Multi.createFrom().items(s.split(" ")))
            .onItem().invoke(s -> log.info("Sending: {}", s))
            .onItem().call(s -> Uni.createFrom().completionStage(emitter.send(s)))
            .onItem().ignore()
            .onItem().invoke(message::ack)
            .toUni();
    }

    private static CompletionStage<Void> ack(Message<String> message) {
        log.info("Ack message: {}", message.getPayload() );
        return message.ack();
    }
}
