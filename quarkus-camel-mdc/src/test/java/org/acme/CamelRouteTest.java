package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;

@QuarkusTest
class CamelRouteTest {

    @Inject
    private ProducerTemplate producer;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    @Test
    void name() throws InterruptedException {
        executor.execute(() -> producer.sendBody("direct:start", new Payload("1", "body 1")));
        executor.execute(() -> producer.sendBody("direct:start", new Payload("2", "body 2")));
        Thread.sleep(10000);
    }
}