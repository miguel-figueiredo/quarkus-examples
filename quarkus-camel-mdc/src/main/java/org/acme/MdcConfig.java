package org.acme;

import org.jboss.logging.MDC;

public class MdcConfig {
    void execute(Payload payload) {
        MDC.put("payload-id", payload.getId());
    }
}