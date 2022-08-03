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
package org.acme;

import io.smallrye.jwt.algorithm.SignatureAlgorithm;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.util.KeyUtils;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class JwtManager {

    @Inject
    private JWTParser parser;

    @ConfigProperty(name = "smallrye.jwt.sign.algorithm")
    private SignatureAlgorithm signatureAlgorithm;

    @ConfigProperty(name = "jwt.duration")
    private Duration duration;

    public String createJwt() {
        return Jwt.claim("id", "id")
            .expiresIn(duration)
            .jws().algorithm(signatureAlgorithm)
            .sign();
    }

    public JsonWebToken validateJwt(final String jwt) throws ParseException {
        return parser.parse(jwt);
    }
}