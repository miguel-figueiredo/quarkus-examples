package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.auth.principal.ParseException;
import javax.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.junit.jupiter.api.Test;

@QuarkusTest
class JwtProducerTest {

    @Inject
    private JwtManager subject;

    @Test
    void validJwt() throws Exception {
        String jwt = subject.createJwt();

        JsonWebToken jsonWebToken = subject.validateJwt(jwt);

        String id = jsonWebToken.getClaim("id");

        assertEquals("id", id);
    }

    @Test
    void expiredJwt() throws Exception {
        String jwt = subject.createJwt();

        // Yes this sucks
        Thread.sleep(1000);

        assertThrows(ParseException.class, () -> subject.validateJwt(jwt));
    }
}