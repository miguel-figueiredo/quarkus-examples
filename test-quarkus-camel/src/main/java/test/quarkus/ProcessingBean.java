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
package test.quarkus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class ProcessingBean {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void process(final String body) {
        System.out.println("Received body in bean: " + body);
        final TestEntity testEntity = new TestEntity(body);
        entityManager.persist(testEntity);
    }
}
