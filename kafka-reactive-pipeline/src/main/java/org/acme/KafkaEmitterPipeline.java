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
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
public class KafkaEmitterPipeline {

    @RestClient
    private GreetingService greetingService;

    @Inject
    @Channel("words-emitter-out")
    private Emitter<String> emitter;

    /**
     * The commit is only done after all the messages are sent.
     * If there is an error in the pipeline the commit is not done and the
     * original message can be received again.
     **/
    @Incoming("words-emitter-in")
    @NonBlocking
    public Uni<Void> execute(String message) {
        return Uni.createFrom().item(message)
            .onItem().invoke(s -> log.info("Received: {}", s))
            .onItem().delayIt().by(Duration.ofSeconds(5))
            .onItem().transformToMulti(s -> Multi.createFrom().items(s.split(" ")))
            .onItem().invoke(s -> log.info("Sending: {}", s))
            .onItem().invoke(this::send)
            .onItem().ignore()
            .toUni();
    }

    private CompletionStage<Void> send(String message) {
        maybeFail();
        return emitter.send(message);
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
