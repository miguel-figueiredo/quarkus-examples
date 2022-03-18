/*
 *
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2022
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 *
 */
package test.quarkus;

import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduled.ConcurrentExecution;
import io.smallrye.reactive.messaging.kafka.KafkaClientService;
import io.smallrye.reactive.messaging.kafka.KafkaConsumer;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

@ApplicationScoped
@Slf4j
public class MessageConsumer {

    @Inject
    private KafkaClientService kafkaClientService;

    @Scheduled(every = "10s", concurrentExecution = ConcurrentExecution.SKIP)
    public void execute() {
        final KafkaConsumer<Object, Object> kafkaConsumer = kafkaClientService.getConsumer("messages-incoming");

        kafkaConsumer
            .runOnPollingThread(this::pollLoop)
            .onItem().invoke(() -> log.info("Finished polling"))
            .onItem().invoke(() -> kafkaConsumer.pause())
            .subscribe().asCompletionStage();
    }

    private void pollLoop(final Consumer<Object, Object> consumer) {
        AtomicBoolean getNext = new AtomicBoolean(true);
        if(consumer.subscription().isEmpty()) {
            consumer.subscribe(List.of("messages-topic"));
        }
        log.info("Consumer subscription: {}", consumer.subscription());

        while(getNext.get()) {
            log.info("Polling");
            final ConsumerRecords<Object, Object> consumerRecords = consumer.poll(Duration.ofMillis(1000));
            consumerRecords
                .forEach(message -> {
                    log.info("Received message: {}, timestamp: {}, offset: {}",
                    message.value(), LocalDateTime.ofInstant(Instant.ofEpochMilli(message.timestamp()), ZoneId.systemDefault()), message.partition(), message.offset());
                    if(message.timestamp() > System.currentTimeMillis() - 10000) {
                        log.info("Pausing poll loop");
                        getNext.set(false);
                    }
                });
            consumer.commitSync();
            log.info("Committed");
        }
    }
}
