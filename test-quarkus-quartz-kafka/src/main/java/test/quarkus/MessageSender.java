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
import java.time.LocalDateTime;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
@Slf4j
public class MessageSender {

    @Inject
    @Channel("messages-outgoing")
    Emitter<String> emitter;

    @Scheduled(every = "1s", concurrentExecution = ConcurrentExecution.SKIP)
    public void execute() {
        final String timestamp = LocalDateTime.now().toString();
        log.info("Sending message: {}", timestamp);
        emitter.send(timestamp);
    }
}
