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

import javax.ws.rs.container.ContainerRequestContext;
import org.jboss.logging.MDC;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

public class MdcLoggingFilters {
    @ServerRequestFilter
    @MdcLogged
    public void getFilter(ContainerRequestContext ctx) {
        MDC.put("host", ctx.getHeaders().get("Host"));
    }
}