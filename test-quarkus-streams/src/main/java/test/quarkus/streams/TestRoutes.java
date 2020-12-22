/*
 *
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2020
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 *
 */
package test.quarkus.streams;

import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Multi;
import io.vertx.ext.web.RoutingContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TestRoutes {

    public static final String INPUT_FILE = "/tmp/input.txt";

    @Route(path = "/lines")
    Multi<String> list(RoutingContext context) throws IOException {
        return Multi.createFrom().items(Files.lines((Path.of(INPUT_FILE))));
    }
}
