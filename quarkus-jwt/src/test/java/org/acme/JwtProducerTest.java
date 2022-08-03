package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.auth.principal.ParseException;
import javax.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class JwtProducerTest {

    @Inject
    private JwtManager subject;

    @Test
    void jwt() throws ParseException {
        String jwt = subject.createJwt();
        JsonWebToken jsonWebToken = subject.validateJwt(jwt);
        System.out.println(jwt);
        String id = jsonWebToken.getClaim("id");

        assertEquals("id", id);
    }
}