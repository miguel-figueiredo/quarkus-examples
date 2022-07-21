/**
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2022
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 */
package org.acme;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

import java.util.Random;
import org.apache.camel.builder.RouteBuilder;

/**
 * See https://camel.apache.org/camel-quarkus/2.10.x/user-guide/defining-camel-routes.html
 */
public class CamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        getContext().setUseMDCLogging(true);
        getContext().setUseBreadcrumb(true);

        from(direct("start"))
            .routeId("start_route")
            .bean(new MdcConfig())
            .log("${body}")
            .delay((long) (Math.random() * 2000))
            .to(direct("end"));

        from(direct("end"))
            .routeId("end_route")
            .log("${body}");
    }
}