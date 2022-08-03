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
    ProducerTemplate producer;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    @Test
    void throttle() throws InterruptedException {
        executor.submit(() -> producer.sendBody("direct:start", "1"));
        executor.submit(() -> producer.sendBody("direct:start", "2"));
        executor.submit(() -> producer.sendBody("direct:start", "3"));
        executor.submit(() -> producer.sendBody("direct:start", "4"));
        executor.submit(() -> producer.sendBody("direct:start", "5"));
        executor.submit(() -> producer.sendBody("direct:start", "6"));
        executor.submit(() -> producer.sendBody("direct:start", "7"));
        executor.submit(() -> producer.sendBody("direct:start", "8"));
        executor.submit(() -> producer.sendBody("direct:start", "9"));
        executor.submit(() -> producer.sendBody("direct:start", "10"));

        Thread.sleep(10000);
    }
}