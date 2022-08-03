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

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import java.time.Duration;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class JwtManager {

    @Inject
    private JWTParser parser;

    // Created with: openssl ecparam -name prime256v1 -genkey -noout
    private static final String SECRET =
        "MHcCAQEEIBNrdGICVOE5Cox7NPGY02/6HTb2iU8TED7DmlJHLJSjoAoGCCqGSM49\n"
        + "AwEHoUQDQgAEJiBEVs4sWMnR1+2YJAzflyTqIoaiETOqWcDzNydsjX4EIsqo2jcc\n"
        + "E4iAJGMxqzm4vQLr0H73L44hGbm2qTGQsg==";

    public String createJwt() {
        return Jwt.claim("id", "id")
            .upn("test")
            .expiresIn(Duration.ofHours(1))
            .signWithSecret(SECRET);
    }

    public JsonWebToken validateJwt(final String jwt) throws ParseException {
        return parser.verify(jwt, SECRET);
    }
}