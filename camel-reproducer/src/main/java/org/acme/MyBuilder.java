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

import javax.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

@ApplicationScoped
public class MyBuilder extends EndpointRouteBuilder implements MyInterface {
    @Override
    public void configure() throws Exception {

    }

    @Override
    public void method1() {
    }

    @Override
    public void method2() {
    }

    @Override
    public void method3() {
    }

    @Override
    public void method4() {
    }

    @Override
    public void method5() {
    }

    @Override
    public void method6() {
    }
}