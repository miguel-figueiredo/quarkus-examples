package test.quarkus;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

import io.quarkus.test.junit.QuarkusTest;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

@QuarkusTest
class RetryTest {

    @Inject
    private ProducerTemplate producer;

    @Inject
    private CamelContext context;

    @Test
    void test() {
        send(new Utterance("1", "Hello World!"));
    }

    void send(final Utterance utterance) {
        producer.sendBody("direct:start-retry", utterance);
    }

    @AllArgsConstructor
    @ToString
    private static class Utterance {
        private String accountId;
        private String utterance;

        public String getAccountId() {
            return accountId;
        }

        public String getUtterance() {
            return utterance;
        }
    }

    @ApplicationScoped
    private static class TestRouteBuilder extends RouteBuilder {

        @Inject
        private FirstFailureBean firstFailureBean;

        @Inject
        private SecondFailureBean secondFailureBean;

        @Inject
        private ThirdFailureBean thirdFailureBean;

        @Override
        public void configure() throws Exception {
            // Context/Scope error handler
            errorHandler(defaultErrorHandler()
                .onExceptionOccurred(new ExceptionProcessor())
                .retryAttemptedLogLevel(LoggingLevel.WARN)
                .useExponentialBackOff()
                .backOffMultiplier(2)
                .maximumRedeliveryDelay(60000)
                .maximumRedeliveries(10));

            // Error handler for a specific exception
            // In this case just overrides some properties of the context error handler,
            // and uses all the others.
            onException(SecondFailureBean.BeanException.class)
                .retryAttemptedLogLevel(LoggingLevel.INFO)
                .redeliveryDelay(500)
                .maximumRedeliveries(10);

            from(direct("start-retry"))
                .log("${body}")
                .bean(firstFailureBean)
                .bean(secondFailureBean)
                .to(direct("retry-subroute"));

            from(direct("retry-subroute"))
                // Route error handler which takes precedence over the context error handler.
                .errorHandler(defaultErrorHandler()
                    .retryAttemptedLogLevel(LoggingLevel.ERROR)
                    .redeliveryDelay(100)
                    .maximumRedeliveries(10))
                .bean(thirdFailureBean);
        }
    }

    @Slf4j
    private static class ExceptionProcessor implements Processor {

        @Override
        public void process(final Exchange exchange) throws Exception {
            log.error("Processing exception: {}" , exchange.getException().getClass());
        }
    }

    @ApplicationScoped
    @Slf4j
    private static class FirstFailureBean {

        private int count;

        public Utterance execute(Utterance utterance) {
            if(count < 2) {
                log.error("Count First Bean: {}", count);
                count++;
                throw new BeanException();
            }
            log.info("Received utterance: {}", utterance);
            return utterance;
        }

        public static class BeanException extends RuntimeException {

        }
    }

    @ApplicationScoped
    @Slf4j
    private static class SecondFailureBean {

        private int count;

        public Utterance execute(Utterance utterance) {
            if(count < 2) {
                log.error("Count Second Bean: {}", count);
                count++;
                throw new BeanException();
            }
            log.info("Received utterance: {}", utterance);
            return utterance;
        }

        public static class BeanException extends RuntimeException {

        }
    }

    @ApplicationScoped
    @Slf4j
    private static class ThirdFailureBean {

        private int count;

        public Utterance execute(Utterance utterance) {
            if(count < 2) {
                log.error("Count Third Bean: {}", count);
                count++;
                throw new BeanException();
            }
            log.info("Received utterance: {}", utterance);
            return utterance;
        }

        public static class BeanException extends RuntimeException {

        }
    }
}