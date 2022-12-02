package org.acme;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.reactivestreams.Publisher;

@ApplicationScoped
@Slf4j
public class KafkaManualAckPipeline {

    @RestClient
    private GreetingService greetingService;

    /**
     * If there is an error after the ack (and before a message is sent),
     * the original message is not consumed again.
     **/
    @Incoming("words-manual-ack-in")
    @Outgoing("words-manual-ack-out")
    @NonBlocking
    public Publisher<Message<String>> execute(Message<String> message) {
        return Uni.createFrom().item(message.getPayload())
            .onItem().invoke(s -> log.info("Received: {}", s))
            .onItem().delayIt().by(Duration.ofSeconds(5))
            .onItem().transformToMulti(s -> Multi.createFrom().items(s.split(" ")))
            .onItem().invoke(() -> ack(message))
            .onItem().invoke(() -> maybeFail())
            .onItem().invoke(s -> log.info("Sending: {}", s))
            .onItem().transform(Message::of);
    }

    private void maybeFail() {
        if(new Random().nextBoolean()) {
            throw new RuntimeException();
        }
    }

    private static CompletionStage<Void> ack(Message<String> message) {
        log.info("Ack message: {}", message.getPayload() );
        return message.ack();
    }
}
