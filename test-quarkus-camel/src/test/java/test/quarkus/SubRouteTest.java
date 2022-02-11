package test.quarkus;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.mock;
import static org.apache.camel.support.builder.ExpressionBuilder.bodyExpression;
import static org.apache.camel.support.builder.ExpressionBuilder.constantExpression;

import io.quarkus.test.junit.QuarkusTest;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

@QuarkusTest
class SubRouteTest {

    @Inject
    private ProducerTemplate producer;

    @Inject
    private CamelContext context;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Test
    void test() throws InterruptedException, FileNotFoundException {
       send(new Utterance("1", "Hello1"));
       send(new Utterance("1", "World1"));
       send(new Utterance("2", "Hello2"));
       send(new Utterance("3", "Hello3"));
       send(new Utterance("1", "Bye1"));
       send(new Utterance("4", "Hello4"));
       send(new Utterance("1", "Bye1"));
       send(new Utterance("4", "Hello4"));

        Thread.sleep(5000);
    }

    void send(final Utterance utterance) {
        executorService.execute(() -> producer.sendBody("direct:start", utterance));
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
        AwsConfiguration awsConfiguration;

        @Override
        public void configure() throws Exception {
           from(direct("start"))
               .throttle(constantExpression(1), bodyExpression(Utterance.class, Utterance::getAccountId))
                    .asyncDelayed()
               .log("${body}")
               .to(mock("output"));
        }
    }
}