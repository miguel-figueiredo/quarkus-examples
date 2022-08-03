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

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class CamelRoute extends EndpointRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(direct("start"))
            .to(direct("a"))
            .to(direct("b"));

        from(direct("a"))
            .throttle(1)
            .timePeriodMillis(1000)
            .asyncDelayed()
            .log("Executing A : ${body}")
            .delay(500)
            .log("Executed A: ${body}");

        from(direct("b"))
            .throttle(1)
            .timePeriodMillis(1000)
            .asyncDelayed()
            .log("Executing B : ${body}")
            .delay(500)
            .log("Executed B: ${body}");

        from(direct("a-throttle"))
            .to(direct("throttle"))
            .log("Executing A : ${body}")
            .delay(500)
            .log("Executed A: ${body}");


        from(direct("b-throttle"))
            .to(direct("throttle"))
            .log("Executing B : ${body}")
            .delay(500)
            .log("Executed B: ${body}");

        from(direct("throttle"))
            .throttle(1)
            .timePeriodMillis(1000)
            .asyncDelayed();
    }
}