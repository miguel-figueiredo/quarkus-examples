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
package test;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import lombok.extern.slf4j.Slf4j;

@Interceptor
@Count
@Slf4j
public class CountInterceptor {

    @AroundInvoke
    public Object auditMethod(InvocationContext ctx) throws Exception {
        log.info("Intercepting: " + ctx.getMethod().getAnnotation(Count.class).value());
        return ctx.proceed();
    }

}
